package com.hyjf.batch.sell.daily;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.util.GetDate;
import com.hyjf.mybatis.mapper.auto.HolidaysConfigNewMapper;
import com.hyjf.mybatis.mapper.auto.SellDailyDistributionMapper;
import com.hyjf.mybatis.mapper.auto.SellDailyMapper;
import com.hyjf.mybatis.mapper.customize.HolidaysConfigNewCustomizeMapper;
import com.hyjf.mybatis.mapper.customize.SellDailyCustomizeMapper;
import com.hyjf.mybatis.model.auto.*;
import com.hyjf.mybatis.util.mail.MailUtil;

/**
 * @author xiasq
 * @version DailyAutoSendServiceImpl, v0.1 2018/8/3 10:00
 */
@Service
public class DailyAutoSendServiceImpl implements DailyAutoSendService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SellDailyMapper sellDailyMapper;
	@Autowired
	private SellDailyCustomizeMapper sellDailyCustomizeMapper;
	@Autowired
	private SellDailyDistributionMapper sellDailyDistributionMapper;
	@Autowired
	private HolidaysConfigNewMapper holidaysConfigNewMapper;
	@Autowired
	private HolidaysConfigNewCustomizeMapper holidaysConfigNewCustomizeMapper;

	/**
	 * 获取后台发送邮件配置
	 * 
	 * @return
	 */
	@Override
	public List<SellDailyDistribution> listSellDailyDistribution() {
		SellDailyDistributionExample example = new SellDailyDistributionExample();
		SellDailyDistributionExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(1);
		List<SellDailyDistribution> list = sellDailyDistributionMapper.selectByExample(example);
		return list;
	}

	/**
	 * 判断某天是否是工作日
	 *
	 * @param date
	 * @return
	 */
	@Override
	public boolean isWorkdateOnSomeDay(Date date) {
		HolidaysConfigNewExample example = new HolidaysConfigNewExample();
		HolidaysConfigNewExample.Criteria criteria = example.createCriteria();
		criteria.andDayTimeEqualTo(date);
		List<HolidaysConfigNew> list = holidaysConfigNewMapper.selectByExample(example);

		if (CollectionUtils.isEmpty(list)) {
			throw new RuntimeException("日历配置错误...");
		}
		HolidaysConfigNew holidaysConfigNew = list.get(0);
		if (holidaysConfigNew.getHolidayFlag() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当天是不是当月第一个工作日
	 * 
	 * @return
	 */
	@Override
	public boolean isTodayFirstWorkdayOnMonth() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		int firstWorkday = holidaysConfigNewCustomizeMapper.selectFirstWorkdayOnMonth(currentYear, currentMonth);
		int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		return firstWorkday == currentDay ? true : false;
	}

	/**
	 * 发送销售日报邮件
	 * 
	 * @param sellDailyDistribution
	 */
	@Override
	public void sendMail(SellDailyDistribution sellDailyDistribution) {
		String[] toEmail = sellDailyDistribution.getEmail().split(";");
		if (toEmail.length < 0) {
			return;
		}
		String dateStr = GetDate.getFormatDateStr();
		String fileName =  "销售日报-" + dateStr + ".xls" ;
		// 将excel作为附件
		InputStreamSource is = this.drawExcel(dateStr);
		logger.info("开始发送销售日报邮件>>>>>>>>>>>>>>>>>>>>>toEmail:" + JSONObject.toJSONString(toEmail) + "");
		// 邮件内容
		String content = "";
		String subject = "【销售日报】";
		try {
			// 包含附件
			MailUtil.sendAttachmentsMailOnPort25(toEmail, subject, content, fileName, is);
			//MailUtil.sendAttachmentsMailOnPort465(toEmail, subject, content, new String[]{fileName}, is);
			logger.info("发送销售日报成功>>>>>>>>>>>>>>>>>>>>>");
		} catch (Exception e) {
			logger.error("发送销售日报失败>>>>>>>>>>>>>>>>>>>>>", e);
		}
	}

	/**
	 * 填充数据
	 *
	 * @param dateStr
	 * @return
	 */
	private InputStreamSource drawExcel(String dateStr) {
		// 表格sheet名称
		String sheetName = "销售数据日报表";
		String[] titles = new String[] { "序号", "一级分部", "二级分部", "门店数量", "本月累计规模业绩", "本月累计已还款", "上月对应累计规模业绩", "环比增速",
				"本月累计提现", "提现占比", "本月累计充值", "本月累计年化业绩", "上月累计年化业绩", "环比增速", "昨日规模业绩", "昨日还款", "昨日年化业绩", "昨日提现", "昨日充值",
				"昨日净资金流（充值-提现）", "当日待还", "昨日注册数", "其中充值≥3000人数", "其中出借≥3000人数", "本月累计出借3000以上新客户数" };
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

		Map<Integer, List<SellDaily>> resultMap = this.selectDrawExcelSourceData(dateStr);

		if (!CollectionUtils.isEmpty(resultMap)) {
			int rowNum = 1;
			// 序号列
			int orderNum = 0;

			for (int drawOrder : resultMap.keySet()) {
				List<SellDaily> sellDailies = resultMap.get(drawOrder);
				HSSFCellStyle cellStyle = this.createNomalCellStyle(workbook);
				for (int i = 0; i < sellDailies.size(); i++) {
					SellDaily sellDaily = sellDailies.get(i);

					// 本月累计规模业绩和上月对应累计规模业绩为0的数据不显示
					if (BigDecimal.ZERO.compareTo(sellDaily.getInvestTotalMonth()) >= 0
							&& BigDecimal.ZERO.compareTo(sellDaily.getInvestTotalPreviousMonth()) >= 0
							&& BigDecimal.ZERO.compareTo(sellDaily.getNonRepaymentToday()) >= 0
							&& BigDecimal.ZERO.compareTo(sellDaily.getRepaymentTotalMonth()) >= 0
							&& BigDecimal.ZERO.compareTo(sellDaily.getWithdrawTotalMonth()) >= 0) {
						logger.info("{},{}机构无销售业务，疑似关停，不显示。", sellDaily.getPrimaryDivision(),
								sellDaily.getTwoDivision());
						continue;
					}


					// 1. 插入运营中心汇总行
					if (drawOrder == 2 && "惠众".equals(sellDaily.getPrimaryDivision())) {
						Row row = sheet.createRow(++rowNum);
						HSSFCellStyle sumOCCellStyle = createTotalCellStyle(row, sheet, workbook, "运营中心汇总", rowNum,
								HSSFColor.GOLD.index);
						SellDaily sumOCSellDaily = getSumSellDaily(1);
						for (int celLength = 3; celLength < titles.length; celLength++) {
							// 创建相应的单元格
							Cell cell = row.createCell(celLength);
							cell.setCellStyle(sumOCCellStyle);
							setCellValue(workbook, cell, celLength, sumOCSellDaily);
						}

					}

					// 2. 插入普通行
					Row row = sheet.createRow(++rowNum);
					orderNum++;
					for (int celLength = 0; celLength < titles.length; celLength++) {
						Cell cell = row.createCell(celLength);
						cell.setCellStyle(cellStyle);
						setCellValue(cell, celLength, sellDaily, orderNum);
					}
				}

				// 3. 插入一级分部合计行
				Row row = sheet.createRow(++rowNum);
				HSSFCellStyle sumPrimaryDivisionCellStyle = createTotalCellStyle(row, sheet, workbook,
						concatPrimaryDivisionTotalTitle(drawOrder), rowNum, HSSFColor.LIGHT_TURQUOISE.index);
				SellDaily sumPrimaryDivisionSellDaily = getSumSellDaily(2, drawOrder);
				// 第三列到第24列计算合计数据
				for (int i = 3; i < titles.length; i++) {
					Cell totalCell = row.createCell(i);
					totalCell.setCellStyle(sumPrimaryDivisionCellStyle);
					setCellValue(workbook, totalCell, i, sumPrimaryDivisionSellDaily);
				}
			}

			// 4. 插入总合计行
			Row row = sheet.createRow(++rowNum);
			HSSFCellStyle allTotalCellStyle = createTotalCellStyle(row, sheet, workbook, "合计", rowNum,
					HSSFColor.YELLOW.index);
			SellDaily allTotalSellDaily = getSumSellDaily(3);
			for (int i = 3; i < titles.length; i++) {
				Cell totalCell = row.createCell(i);
				totalCell.setCellStyle(allTotalCellStyle);
				setCellValue(workbook, totalCell, i, allTotalSellDaily);
			}
		}

		InputStreamSource is = null;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
			workbook.write(os);
			is = new ByteArrayResource(os.toByteArray());
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}

	/**
	 * 创建excel基本样式
	 * 
	 * @param workbook
	 * @param titles
	 * @param sheetName
	 * @return
	 */
	private static HSSFSheet createHSSFWorkbookTitle(HSSFWorkbook workbook, String[] titles, String sheetName) {

		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(sheetName);

		// ----------------标题样式---------------------
		// 标题样式
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		Font ztFont = workbook.createFont();
		// 字体设置
		ztFont.setColor(Font.COLOR_NORMAL);
		// 将字体大小设置为18px
		ztFont.setFontHeightInPoints((short) 18);
		// 将“宋体”字体应用到当前单元格上
		ztFont.setFontName("宋体");
		// 加粗
		ztFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleStyle.setFont(ztFont);

		// 设置前景填充样式
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 淡黄色 https://www.cnblogs.com/caiyun/archive/2012/08/22/2650239.html 颜色对比
		titleStyle.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);

		// ----------------单元格样式----------------------------------
		// 表格样式
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		Font cellFont = workbook.createFont();
		// 字体设置
		cellFont.setColor(Font.COLOR_NORMAL);
		// 将字体大小设置为18px
		cellFont.setFontHeightInPoints((short) 10);
		// 字体应用到当前单元格上
		cellFont.setFontName("宋体");
		cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(cellFont);
		// 设置自动换行
		cellStyle.setWrapText(true);
		// 设置前景填充样式
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);

		// ----------------------创建第一行---------------
		// 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
		Row row = sheet.createRow(0);
		// 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
		Cell cell = row.createCell(0);
		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 24));
		// 设置单元格内容

		cell.setCellValue("销售数据日报表-- " + GetDate.getFormatDateStr() + "                    单位：万元");
		cell.setCellStyle(titleStyle);

		row = sheet.createRow(1);
		for (int celLength = 0; celLength < titles.length; celLength++) {
			// 创建相应的单元格
			cell = row.createCell(celLength);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(titles[celLength]);
		}

		return sheet;
	}

	/**
	 * 查询excel填充数据源
	 * 
	 * @param dateStr
	 * @return
	 */
	private Map<Integer, List<SellDaily>> selectDrawExcelSourceData(String dateStr) {
		SellDailyExample example = new SellDailyExample();
		SellDailyExample.Criteria criteria = example.createCriteria();
		criteria.andDateStrEqualTo(dateStr);
		example.setOrderByClause(" id asc ");
		List<SellDaily> list = sellDailyMapper.selectByExample(example);
		Assert.notNull(list, "sell daily must be not null...");
		return this.slicingDepartmentData(list);
	}

	/**
	 * 根据类型查询合计
	 * 
	 * @param type
	 * @param drawOrder
	 * @return
	 */
	private SellDaily getSumSellDaily(int type, int drawOrder) {
		SellDaily sumSellDaily = null;
		switch (type) {
		case 1:
			sumSellDaily = sellDailyCustomizeMapper.selectOCSum(GetDate.getFormatDateStr());
			break;
		case 2:
			sumSellDaily = sellDailyCustomizeMapper.selectPrimaryDivisionSum(GetDate.getFormatDateStr(), drawOrder);
			break;
		case 3:
			sumSellDaily = sellDailyCustomizeMapper.selectAllSum(GetDate.getFormatDateStr());
			break;
		default:
			throw new RuntimeException("错误的传参数,type is : " + type);
		}

		Assert.notNull(sumSellDaily, "数据缺失");
		return sumSellDaily;
	}

	/**
	 * 根据类型查询合计-重载
	 * 
	 * @param type
	 * @return
	 */
	private SellDaily getSumSellDaily(int type) {
		return getSumSellDaily(type, 0);
	}

	/**
	 * 拼接合计标题
	 * 
	 * @param drawOrder
	 * @return
	 */
	private String concatPrimaryDivisionTotalTitle(int drawOrder) {
		String totalTitleName = "合计";
		switch (drawOrder) {
		case 1:
			totalTitleName = "纳觅".concat(totalTitleName);
			break;
		case 2:
			totalTitleName = "惠众".concat(totalTitleName);
			break;
		case 3:
			totalTitleName = "裕峰瑞".concat(totalTitleName);
			break;
		case 4:
			totalTitleName = "其它".concat(totalTitleName);
			break;
		}
		return totalTitleName;
	}

	/**
	 * 创建普通行样式
	 * 
	 * @param workbook
	 * @return
	 */
	private HSSFCellStyle createNomalCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		return cellStyle;
	}

	/**
	 * 创建汇总行样式
	 * 
	 * @param row
	 * @param sheet
	 * @param workbook
	 * @param cellValue
	 * @param rowNum
	 * @param color
	 *            合计行的颜色
	 * @return
	 */
	private HSSFCellStyle createTotalCellStyle(Row row, HSSFSheet sheet, HSSFWorkbook workbook, String cellValue,
			int rowNum, short color) {
		Cell cell = row.createCell(0);
		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));
		cell.setCellValue(cellValue);
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置前景填充样式
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 淡黄色 https://www.cnblogs.com/caiyun/archive/2012/08/22/2650239.html 颜色对比
		cellStyle.setFillForegroundColor(color);

		Font cellFont = workbook.createFont();
		// 字体设置
		cellFont.setColor(Font.COLOR_NORMAL);
		// 将字体大小设置为18px
		cellFont.setFontHeightInPoints((short) 10);
		// 字体应用到当前单元格上
		cellFont.setFontName("Calibri");
		cellStyle.setFont(cellFont);

		cell.setCellStyle(cellStyle);
		return cellStyle;
	}

	/**
	 * 填充cell值 -重载方法
	 * 
	 * @param workbook
	 * @param cell
	 * @param celLength
	 * @param sellDaily
	 * @return
	 */
	private Cell setCellValue(HSSFWorkbook workbook, Cell cell, int celLength, SellDaily sellDaily) {
		return setCellValue(cell, celLength, sellDaily, 0);
	}

	/**
	 * 填充cell值
	 * 
	 * @param workbook
	 * @param cell
	 * @param celLength
	 * @param sellDaily
	 * @return
	 */
	private Cell setCellValue(Cell cell, int celLength, SellDaily sellDaily, int sortNo) {
		switch (celLength) {
		case 0:
			cell.setCellValue(sortNo);
			break;
		case 1:
			cell.setCellValue(sellDaily.getPrimaryDivision());
			break;
		case 2:
			cell.setCellValue(sellDaily.getTwoDivision());
			break;
		case 3:
			Integer cellStoreNum = sellDaily.getStoreNum();
			cell.setCellValue(cellStoreNum);
			break;
		case 4:
			cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getInvestTotalMonth()));
			break;
		case 5:
			cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getRepaymentTotalMonth()));
			break;
		case 6:
			cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getInvestTotalPreviousMonth()));
			break;
		case 7:
			String investRatioGrowth = sellDaily.getInvestRatioGrowth();
			cell.setCellValue(investRatioGrowth);
			break;
		case 8:
			cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getWithdrawTotalMonth()));
			break;
		case 9:
			String withdrawRate = sellDaily.getWithdrawRate();
			cell.setCellValue(withdrawRate);
			break;
		case 10:
			cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getRechargeTotalMonth()));
			break;
		case 11:
			cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getInvestAnnualTotalMonth()));
			break;
		case 12:
			cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getInvestAnnualTotalPreviousMonth()));
			break;
		case 13:
			String investAnnularRatioGrowth = sellDaily.getInvestAnnularRatioGrowth();
			cell.setCellValue(investAnnularRatioGrowth);
			break;
		case 14:
			cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getInvestTotalYesterday()));
			break;
		case 15:
			cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getRepaymentTotalYesterday()));
			break;
		case 16:
			cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getInvestAnnualTotalYesterday()));
			break;
		case 17:
			cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getWithdrawTotalYesterday()));
			break;
		case 18:
			cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getRechargeTotalYesterday()));
			break;
		case 19:
			cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getNetCapitalInflowYesterday()));
			break;
		case 20:
			cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getNonRepaymentToday()));
			break;
		case 21:
			Integer registerTotalYesterday = sellDaily.getRegisterTotalYesterday();
			cell.setCellValue(registerTotalYesterday.toString());
			break;
		case 22:
			Integer rechargeGt3000UserNum = sellDaily.getRechargeGt3000UserNum();
			cell.setCellValue(rechargeGt3000UserNum.toString());
			break;
		case 23:
			Integer investGt3000UserNum = sellDaily.getInvestGt3000UserNum();
			cell.setCellValue(investGt3000UserNum.toString());
			break;
		case 24:
			Integer investGt3000MonthUserNum = sellDaily.getInvestGt3000MonthUserNum();
			cell.setCellValue(investGt3000MonthUserNum.toString());
			break;
		default:
		}
		return cell;
	}

	/**
	 * 四舍五入从 BigDecimal 取出整数 ，转成字符串
	 *
	 * @param bigDecimal
	 * @return
	 */
	private String cutIntegerFromBigDecimal(BigDecimal bigDecimal) {
		return bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 切割部门
	 * 
	 * @param list
	 * @return
	 */
	private Map<Integer, List<SellDaily>> slicingDepartmentData(List<SellDaily> list) {
		Map<Integer, List<SellDaily>> map = new TreeMap<>();

		for (SellDaily bean : list) {
			List<SellDaily> sellDailies = null;
			if (map.containsKey(bean.getDrawOrder())) {
				sellDailies = map.get(bean.getDrawOrder());
			} else {
				sellDailies = new ArrayList<>();
			}
			sellDailies.add(bean);
			map.put(bean.getDrawOrder(), sellDailies);
		}
		return map;
	}
}

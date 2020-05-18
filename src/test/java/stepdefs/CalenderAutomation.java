package stepdefs;

import java.awt.List;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;

import allocator.ReusableMethods;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import listeners.ExtentReportListener;

public class CalenderAutomation extends ExtentReportListener 
{ 
	ReusableMethods RM = new ReusableMethods();
	ExtentTest logInfo=null;
	@Given("^I navigate to calender website$")
	public void navigateToCalenderWebsite() throws ClassNotFoundException, IOException
	{
		try {
			test = extent.createTest(Feature.class, "Verify selection of dates");                         
			test=test.createNode(Scenario.class, "Verify date got select as per expectation");                       
			logInfo=test.createNode(new GherkinKeyword("Given"), "Verify today's date selection");
			driver.get(prop.getProperty("AppURL"));
			RM.reportandscreenshot("DatePickerTitle_CalenderHomePage", "User navigation to calender Website", logInfo, captureScreenShot(driver));
		}
		catch(Exception e)
		{
			testStepHandle("FAIL",driver,logInfo,e);    

		}
	}
	@When("^I verify today's date selected$")
	public void todayDateSelect() throws Exception
	{
		RM.PerformActionOnElement("DateBox_CalenderHomePage", "click", "");
		RM.PerformActionOnElement("TodayHighlightedDate_CalenderHomePage", "click", "");
		String Selecteddate=RM.getTextFromElement("SelectedDate_CalenderHomePage");
		String ActualTodaydate=RM.getCurrentDate("dd/mm/yyyy");
		RM.compareAndreport(ActualTodaydate, Selecteddate, "The highligted date is today date", logInfo, captureScreenShot(driver));
	}
	@And("^I verify selection of \"([^\"]*)\"$")
	public void recentYear2019(String Date) throws Exception
	{
		RM.PerformActionOnElement("DateBox_CalenderHomePage", "click", "");
		String CurrentYear=RM.getTextFromElement("CurrentYear_CalenderHomePage");
		int PresentYear = Integer.parseInt(CurrentYear);
		String []DateArr= Date.split(" ");
		int YearToBeSelected=Integer.parseInt(DateArr[2]);

		while((YearToBeSelected-PresentYear)<0)
		{
			RM.PerformActionOnElement("CalenderPrevIcon_CalenderHomepage", "click", "");	
			CurrentYear=RM.getTextFromElement("CurrentYear_CalenderHomePage");
			PresentYear = Integer.parseInt(CurrentYear);
		}
		while((YearToBeSelected-PresentYear)>0)
		{
			RM.PerformActionOnElement("CalenderNextIcon_CalenderHomePage", "click", "");	
			CurrentYear=RM.getTextFromElement("CurrentYear_CalenderHomePage");
			PresentYear = Integer.parseInt(CurrentYear);
		}
		
		//Month
		String MonthToBeSelected=DateArr[1];
		int KeyOfMonthToBeSelected = RM.getListOfMonthByValue(MonthToBeSelected);
		String CurrentMonth = RM.getTextFromElement("CurrentMonth_CalenderHomePage");
		int KeyOfCurrentMonth = RM.getListOfMonthByValue(CurrentMonth);
		
		while((KeyOfMonthToBeSelected-KeyOfCurrentMonth)<0)
		{
			RM.PerformActionOnElement("CalenderPrevIcon_CalenderHomepage", "click", "");	
			CurrentMonth=RM.getTextFromElement("CurrentMonth_CalenderHomePage");
			KeyOfCurrentMonth = RM.getListOfMonthByValue(CurrentMonth);
		}
		while((KeyOfMonthToBeSelected-KeyOfCurrentMonth)>0)
		{
			RM.PerformActionOnElement("CalenderNextIcon_CalenderHomePage", "click", "");	
			CurrentMonth=RM.getTextFromElement("CurrentMonth_CalenderHomePage");
			KeyOfCurrentMonth = RM.getListOfMonthByValue(CurrentMonth);
		}
		RM.PerformActionOnElement("DateOftheMonth_CalenderHomePage", "clickwithactionclass", DateArr[0]);
		Thread.sleep(5000);
	}
}

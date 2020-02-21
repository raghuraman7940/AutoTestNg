package lomt.pearson.constant;

import java.util.Random;

public class EnglishTestData {

	public static String DESCRIPTOR_VALUE_1 = " UPDATED-" +new Random().nextInt(1000);
	public static String DRAFT_ID ="abcd123";
	public static String BATCH_ID = "batch_06";
	public static String SKILL = "Reading";
	public static String STATUS = "Internal";
	public static String DESCRIPTOR = "Can use '(in order) to' to express purpose and intention."+"UPDATED-" +new Random().nextInt(1000);	
	public static String ATTRIBUTION = "(C)";
	public static String GSE = "60";
	public static String CEFR_LEVEL = "B2 (59-66)";
	public static String C_CATEGORY = "Language functions>Explaining|Language functions>Negotiating, complaining and dealing with problems";
	public static String B_SKILLS = "Discussions>Take part in business discussions|Socialising at work>Making conversation";
	public static String ACADEMIC_SKILLS = "Academic Text Strategies>Skimming for main idea/gist|Academic strategies>Summarising|Academic Text Strategies>Prediction";
	public static String ANCHAR = "Y";
	public static String FUN_NOTION = "N2.1; N2.2; N3.14";
	public static String SOURCE_DES = "source des|source des1";
	public static String SOURCE = "CEFR-J (adapted)";
	public static String ESTIMATED_LEVEL = "A1 (22-29)";
	public static String NOTES = "North USA";
	
	//Add new row test data
	public static String DES_ID_NEW = "GLLA6666X"+new Random().nextInt(9);
	public static String SYLLABUS_NEW = "GL, AL, PL";
	
	public static String Eng_Ingest_EFL_Status ="EFL";
	public static String Eng_Ingest_ESL_Status ="ESL";
	
	public static String Eng_Ingest_Product_Draft_Status = "Draft";
	
	public static String ACADEMIC_SKILLS_NEW = "Comprehension>Identifying specific details";	
	public static String GSE_File = "Gse_Ingestion_Template.xlsx";
	public static String GSE_Reingest_File = "GSERe-ingestion.xlsx";
	public static String Product_Ingest_File ="EngProduct.xlsx";
	public static String GSE_DescId = "GLSI6666D0";
	public static String Eng_Product_UpdatedValue = "English Product Updated";
	public static String Eng_Product_Reingested_Value = "English Product Reingested Value_";
}

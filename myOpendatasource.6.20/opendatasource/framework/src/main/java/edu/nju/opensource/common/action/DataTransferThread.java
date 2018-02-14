/*    */ package edu.nju.opensource.common.action;
/*    */ 
/*    */ import com.onezetta.abenablebean.util.FileUtil;
/*    */ import com.onezetta.hdfs.HdfsConfiguration;
/*    */ import com.onezetta.hdfs.HdfsFileOperator;
/*    */ import java.io.File;
/*    */ 
/*    */ public class DataTransferThread
/*    */ {
/*    */   public void run()
/*    */   {
/* 12 */     String unuploadFile = OpenDataSource.getUnuploadDataDir();
/* 13 */     String uploadedFile = OpenDataSource.getUploadedDataDir();
/*    */ 
/* 15 */     HdfsFileOperator hdfsFileOperator = new HdfsFileOperator();
/*    */     try {
/* 17 */       File[] files = new File(unuploadFile).listFiles();
/* 18 */       for (File f : files)
/*    */         try {
/* 20 */           String fName = f.getName();
/* 21 */           boolean rst = hdfsFileOperator.saveBinaryFile(
/* 22 */             HdfsConfiguration.getHdfsDataPath(), unuploadFile + fName);
/*    */ 
/* 25 */           if (rst) {
/* 26 */             FileUtil.copyFile(unuploadFile + fName, uploadedFile + fName);
/* 27 */             FileUtil.deleteFile(unuploadFile + fName);
/*    */           }
/*    */         } catch (Exception el) {
/* 30 */           el.printStackTrace();
/*    */         }
/*    */     }
/*    */     catch (Exception e) {
/* 34 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opensource.common.action.DataTransferThread
 * JD-Core Version:    0.6.2
 */
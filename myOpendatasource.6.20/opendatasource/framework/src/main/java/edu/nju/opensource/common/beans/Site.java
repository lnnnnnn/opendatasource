/*    */ package edu.nju.opensource.common.beans;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Site
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 4208147845758095885L;
/*    */   private String name;
/*    */   private Link url;
/*    */   private ENation nation;
/*    */   private ELingual lingual;
/*    */   private String uuid;
/*    */   private String note;
/*    */ 
/*    */   public Link getUrl()
/*    */   {
/* 18 */     return this.url;
/*    */   }
/*    */ 
/*    */   public void setUrl(Link url) {
/* 22 */     this.url = url;
/*    */   }
/*    */ 
/*    */   public ENation getNation() {
/* 26 */     return this.nation;
/*    */   }
/*    */ 
/*    */   public void setNation(ENation nation) {
/* 30 */     this.nation = nation;
/*    */   }
/*    */ 
/*    */   public String getNote() {
/* 34 */     return this.note;
/*    */   }
/*    */ 
/*    */   public void setNote(String note) {
/* 38 */     this.note = note;
/*    */   }
/*    */ 
/*    */   public ELingual getLingual() {
/* 42 */     return this.lingual;
/*    */   }
/*    */ 
/*    */   public void setLingual(ELingual lingual) {
/* 46 */     this.lingual = lingual;
/*    */   }
/*    */ 
/*    */   public String getUuid() {
/* 50 */     return this.uuid;
/*    */   }
/*    */ 
/*    */   public void setUuid(String uuid) {
/* 54 */     this.uuid = uuid;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 58 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 62 */     this.name = name;
/*    */   }
@Override
public String toString() {
	return "Site [name=" + name + ", url=" + url + ", nation=" + nation + ", lingual=" + lingual + ", uuid=" + uuid
			+ ", note=" + note + "]";
}


/*    */ }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opensource.common.beans.Site
 * JD-Core Version:    0.6.2
 */
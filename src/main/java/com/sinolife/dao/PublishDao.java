package com.sinolife.dao;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sinolife.model.Publish;

/**
 * 排期dao
 * @author Flystar
 *
 */
@Mapper
public interface PublishDao {
	
    String TABLE_NAME = "Publish";
    String INSERT_FIELDS = "id,publish_desc,attribute,publish_date,manager,verifier,deployer,state,created_date,updated_date";
    String SELECT_FIELDS = "id,publish_desc,attribute,publish_date,manager,verifier,deployer,state,created_date,updated_date";
    
    @Select({"select ", SELECT_FIELDS ,"from ",TABLE_NAME,"order by updated_date limit #{startIndex},#{pageSize}"})
    List<Publish> selectPublishOffset(@Param("startIndex") int startIndex,@Param("pageSize") int pageSize);
    
    @Insert({ "insert into ", TABLE_NAME, "(", INSERT_FIELDS,") values (#{attribute},#{publish_date},#{publish_desc},#{created_date},#{updated_date}"})
    int insertPublish(Publish publish);
    
    @Update({"update ", TABLE_NAME, " set attribute=#{attribute},publish_date=#{publishDate}, manager=#{manager},verifier=#{verifier},deployer=#{deployer},state=#{state} where id=#{id}"})
    void updatePublish(Publish publish);
    
//    @Select("select j.jira_no,j.jira_desc,p.publish_date,pc.changes_desc,pc.project_id,pc.id,"+
//		"(select e.name from jira j,employee e where j.developer_id = e.id and e.role=0) developer_name,"+
//		"(select e.name from jira j,employee e where j.reporter_id = e.id and e.role=2) reporter_name,"+
//		"(select e.name from jira j,employee e where j.tester_id = e.id and e.role=1) tester_name,"+
//		"(select c.company_desc from jira j,company c where j.company_id = c.id) company_desc,"+
//		"(select d.department_desc from jira j,department d where j.department_id = d.id) department_desc "+
//		"from jira j,"+
//		    "publish p,"+
//		    "business b,"+
//		    "jira_project_changes jpc,"+
//		    "project_changes pc "+
//		"WHERE "+
//		    "j.publish_id = p.id"+
//		        " AND j.business_id = b.id"+
//		        " AND j.jira_no = jpc.jira_no"+
//		        " AND jpc.jira_project_id = pc.id"+
//		        " AND b.suite_name = #{suite_name}"+
//		        " AND p.publish_date =#{publish_date}"+
//		" ORDER BY j.updated_date DESC")
//    List<PublishDetail> selectPublishDetail(@Param("suite_name") String suiteName,
//    										@Param("publish_date") String publishDate);
    
//    @Select("select j.jira_no,j.jira_desc,p.publish_date,"+
//		"(select e.name from jira j,employee e where j.developer_id = e.id and e.role=0 and e.state=0) developer_name,"+
//		"(select e.name from jira j,employee e where j.reporter_id = e.id and e.role=2 and e.state=0) reporter_name,"+
//		"(select e.name from jira j,employee e where j.tester_id = e.id and e.role=1 and e.state=0) tester_name,"+
//		"(select c.company_desc from jira j,company c where j.company_id = c.id) company_desc,"+
//		"(select d.department_desc from jira j,department d where j.department_id = d.id) department_desc "+
//		"from jira j,"+
//		    "publish p,"+
//		    "business b "+
//		"WHERE "+
//		    "j.publish_id = p.id "+
//		        "AND j.business_id = b.id "+
//		        "AND b.suite_name = #{suite_name} "+
//		        "AND p.publish_date =#{publish_date} "+
//		"ORDER BY j.updated_date DESC")
//    PublishDetail selectPublishDetail(@Param("suite_name") String suiteName,
//        							 @Param("publish_date") String publishDate);
}

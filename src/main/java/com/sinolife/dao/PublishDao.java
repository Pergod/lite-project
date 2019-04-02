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
 * 
 * @author Flystar
 *
 */
@Mapper
public interface PublishDao {

	String TABLE_NAME = "publish";
	String INSERT_FIELDS = "publish_desc,attribute,publish_date,manager,verifier,deployer,progress,created_date,updated_date,created_user,updated_user";
	String SELECT_FIELDS = "id,publish_desc,attribute,publish_date,manager,verifier,deployer,progress,created_date,updated_date,created_user,updated_user";

	@Select({ "select ", SELECT_FIELDS, "from ", TABLE_NAME, "order by updated_date desc limit #{startIndex},#{pageSize}" })
	List<Publish> selectPublishOffset(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	@Insert({ "insert into ", TABLE_NAME, "(", INSERT_FIELDS,") values (#{publishDesc},#{attribute},#{publishDate},#{manager},#{verifier},#{deployer},#{progress},#{createdDate},#{updatedDate},#{createdUser},#{updatedUser})" })
	int insertPublish(Publish publish);

	@Update({ "update ", TABLE_NAME," set attribute=#{attribute},publish_date=#{publishDate}, manager=#{manager},verifier=#{verifier},deployer=#{deployer},progress=#{progress},updated_date=#{updatedDate},updated_user=#{updatedUser} where id=#{id}" })
	void updatePublish(Publish publish);

	@Select({ "select ", SELECT_FIELDS, "from ", TABLE_NAME, " where id=#{publishId}" })
	Publish selectPublishById(@Param("publishId") int publishId);
	
	@Select({ "select ", SELECT_FIELDS, "from ", TABLE_NAME, " where publish_date=#{publishDate}" })
	Publish selectPublishByDate(@Param("publishDate") String publishDate);
}

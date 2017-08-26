package com.yitianyike.calendar.pullserver.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.dao.VideoDAO;
import com.yitianyike.calendar.pullserver.model.PackageInfo;
import com.yitianyike.calendar.pullserver.model.Video;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;
@Component("videoDAO")
public class VideoDAOImpl extends BaseDAO implements VideoDAO {

	private String videoParams = "id,chinese_name,introduction,players,video_icon,director,video_length,video_type,release_date,"
								+"video_banner,english_name,editor ,producer_region,language,video_format,status ,aid,video_h5_url,seasons,sets,update_cycle,source_name ,video_flag";
	
	@Override
	public List<Video> getAllVideoByType(int aids) {
		Map<String , Object> paramMap = new HashMap<String , Object>();
		String sql = "select "+videoParams+" from cms_video where aid=:aids AND status = 1";
		paramMap.put("aids", aids);
		List<Video> videoList = new ArrayList<Video>();
		try {
			videoList = this.getNamedParameterJdbcTemplate().query(sql, paramMap, new RowMapper<Video>() {
				@Override
				public Video mapRow(ResultSet rs, int rowNum) throws SQLException {
					Video video = new Video();
					video.setAid(rs.getInt("aid"));
					video.setChineseName(rs.getString("chinese_name"));
					video.setDirector(rs.getString("director"));
					video.setId(rs.getInt("id"));
					video.setEditor(rs.getString("editor"));
					video.setEnglishName(rs.getString("english_name"));
					video.setIntroduction(rs.getString("introduction"));
					video.setLanguage(rs.getString("language"));
					video.setPlayers(rs.getString("players"));
					video.setProducerRegion(rs.getString("producer_region"));
					video.setReleaseDate(rs.getLong("release_date"));
					video.setSeasons(rs.getString("seasons"));
					video.setSets(rs.getString("sets"));
					video.setSourceName(rs.getString("source_name"));
					video.setUpdateCycle(rs.getString("update_cycle"));
					video.setVideoBanner(rs.getString("video_banner"));
					video.setVideoFlag(rs.getInt("video_flag"));
					video.setVideoFormat(rs.getString("video_format"));
					video.setVideoH5Url(rs.getString("video_h5_url"));
					video.setVideoIcon(rs.getString("video_icon"));
					video.setVideoLength(rs.getString("video_length"));
					video.setVideoType(rs.getString("video_type"));
					video.setExtendUrl(PropertiesUtil.video_extend);
					return video;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return videoList;
	}

	@Override
	public List<Video> getVideoByType(Integer anchor_id) {
		Map<String , Object> paramMap = new HashMap<String , Object>();
		String sql = "select "+videoParams+" from cms_video where aid=:aids AND status = 1 AND video_flag=1";
		paramMap.put("aids", anchor_id);
		List<Video> videoList = new ArrayList<Video>();
		try {
			videoList = this.getNamedParameterJdbcTemplate().query(sql, paramMap, new RowMapper<Video>() {
				@Override
				public Video mapRow(ResultSet rs, int rowNum) throws SQLException {
					Video video = new Video();
					video.setAid(rs.getInt("aid"));
					video.setChineseName(rs.getString("chinese_name"));
					video.setDirector(rs.getString("director"));
					video.setId(rs.getInt("id"));
					video.setEditor(rs.getString("editor"));
					video.setEnglishName(rs.getString("english_name"));
					video.setIntroduction(rs.getString("introduction"));
					video.setLanguage(rs.getString("language"));
					video.setPlayers(rs.getString("players"));
					video.setProducerRegion(rs.getString("producer_region"));
					video.setReleaseDate(rs.getLong("release_date"));
					video.setSeasons(rs.getString("seasons"));
					video.setSets(rs.getString("sets"));
					video.setSourceName(rs.getString("source_name"));
					video.setUpdateCycle(rs.getString("update_cycle"));
					video.setVideoBanner(rs.getString("video_banner"));
					video.setVideoFlag(rs.getInt("video_flag"));
					video.setVideoFormat(rs.getString("video_format"));
					video.setVideoH5Url(rs.getString("video_h5_url"));
					video.setVideoIcon(rs.getString("video_icon"));
					video.setVideoLength(rs.getString("video_length"));
					video.setVideoType(rs.getString("video_type"));
					video.setExtendUrl(PropertiesUtil.video_extend);
					return video;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return videoList;
	}

}

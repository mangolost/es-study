package mangolost.es.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AreaService {

    private final JdbcTemplate areaJdbcTemplate;

    public AreaService(@Qualifier("areaJdbcTemplate") JdbcTemplate areaJdbcTemplate) {
        this.areaJdbcTemplate = areaJdbcTemplate;
    }

    /**
     * 获取所有行政区数据
     *
     * @return
     */
    public List<Map<String, Object>> getAllData() {
        String sql = "select code, area_name, level, size, population, gdp, gdp_rate, gdp_avg from area.t_area";
        return areaJdbcTemplate.queryForList(sql);
    }

}

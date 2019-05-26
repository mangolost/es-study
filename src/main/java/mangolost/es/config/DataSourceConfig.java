package mangolost.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by mangolost on 2017-09-11
 */
@Configuration
public class DataSourceConfig {

    @Value("${area.elasticsearch.host}")
    private String host;

    @Value("${area.elasticsearch.port}")
    private int port;

    @Bean(name = "areaDataSource")
    @ConfigurationProperties(prefix = "area.datasource")
    public DataSource areaDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "areaJdbcTemplate")
    public JdbcTemplate areaJdbcTemplate(
            @Qualifier("areaDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "restHighLevelClient")
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(host, port, "http")));
    }
}
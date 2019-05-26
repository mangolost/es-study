package mangolost.es.service;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class EsDataService {

    @Value("${area.elasticsearch.index_name}")
    private String index_name;

    @Autowired
    private AreaService areaService;

    private final RestHighLevelClient client;

    public EsDataService(@Qualifier("restHighLevelClient") RestHighLevelClient client) {
        this.client = client;
    }

    /**
     * 导入全部数据
     */
    public void importAllData() throws IOException {
        //1. 从数据库中获取数据
        List<Map<String, Object>> areas = areaService.getAllData();
        if (areas != null && areas.size() > 0) {
            //2. 索引数据
            indexData(areas);
        }
    }

    /**
     * 索引数据
     *
     * @param list 导入数据集
     */
    private void indexData(List<Map<String, Object>> list) throws IOException {

        BulkRequest request = new BulkRequest();

        for (Map<String, Object> map : list) {
            String _docId = (String) map.get("code");
            request.add(new IndexRequest(index_name).id(_docId).source(map));
        }

        BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);

        if (!bulkResponse.hasFailures()) {
            System.out.println("OK");
        }
    }

}

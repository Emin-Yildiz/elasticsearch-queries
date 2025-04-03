package org.example.searchservice.user.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import org.example.searchservice.user.User;
import org.example.searchservice.user.model.response.UserElasticCdcModel;
import org.example.searchservice.util.ElasticSearchQueryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private final ElasticsearchClient elasticsearchClient;

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public UserRepository(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    public Optional<User> findUserById(UUID id) {
        Map<String, String> filter = Map.of("after.id.keyword", id.toString());
        SearchResponse<UserElasticCdcModel> response;
        try {
            Query query = ElasticSearchQueryUtil.createBoolQuery(filter);

            response = elasticsearchClient.search(q -> q
                    .index("users.public.users")
                    .query(query), UserElasticCdcModel.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var user = extractUserFromResponse(response);

        if (user.isEmpty()) {
            return Optional.empty();
        } else if (user.size() > 1) {
            throw new RuntimeException("query did not return a unique result: " + user.size());
        }

        var _user = extractUsersFromUserElasticCdcModel(user);

        return Optional.of(_user.getFirst());
    }

    private List<UserElasticCdcModel> extractUserFromResponse(SearchResponse<UserElasticCdcModel> response) {
        return response
                .hits()
                .hits()
                .stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    private List<User> extractUsersFromUserElasticCdcModel(List<UserElasticCdcModel> invoices) {
        return invoices.stream()
                .map(UserElasticCdcModel::getAfter)
                .collect(Collectors.toList());
    }

}

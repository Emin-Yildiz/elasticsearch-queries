package com.example.searchservice.util;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.function.Supplier;

public class ElasticSearchQueryUtil {

    public static Query createMatchAllQuery() {
        return Query.of(q -> q.matchAll(new MatchAllQuery.Builder().build()));
    }

    // İstediğim bir alanın verdiğim search değeri ile uyuşan (içinde barındıran) değerleri bize getirir.
    public static Supplier<Query> buildQueryForFieldAndValue(String fieldName, String searchValue) {
        return () -> Query.of(q -> q.match(buildMatchQueryForFieldAndValue(fieldName, searchValue)));
    }

    /*
        BOOL QUERY :
            birden fazla sorguyu birleştirmemize olanak sağlayan bir sorgu türüdür.
     */
    public static Query createBoolQuery(Map<String,String> filter) {
        return Query.of(q -> q.bool(boolQuery(filter)));
    }

    public static BoolQuery boolQuery(Map<String, String> filter) {
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        for (Map.Entry<String, String> entry : filter.entrySet()) {
            if (entry.getValue() == null) continue;
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.equals("after.invoice_date") || key.equals("after.place_and_date_of_issue")) {
                String[] dates = value.split(",");
                LocalDate startDate = LocalDate.parse(dates[0]);
                LocalDate endDate = LocalDate.parse(dates[1]);

                long startMicros = startDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1_000_000;
                long endMicros = endDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1_000_000;

                boolQueryBuilder.filter(q -> q
//                        .range(r -> r.
//                                .field(key)
//                                .gte(JsonData.of(startMicros))
//                                .lte(JsonData.of(endMicros))
//                        )
                );
            }else if (key.equals("after.port_of_loading") || key.equals("after.destination")) {
                boolQueryBuilder.filter(q -> q
                        .wildcard(w -> w
                                .field(key)
                                .value(value)
                        )
                );
            } else {
                boolQueryBuilder.filter(matchQuery(key, value));
            }
        }

        return boolQueryBuilder.build();
    }

    // bir arama yaparken öneri sunma mekanızması elastic search'de auto suggest olarak geçer.
    public static Query buildAutoSuggestQuery(String name) {
        return Query.of(q -> q.match(createAutoSuggestMatchQuery(name)));
    }
    public static MatchQuery createAutoSuggestMatchQuery(String name) {
        return new MatchQuery.Builder()
                .field("name")
                .query(name)
                .analyzer("invoices")
                .build();
    }

    // Birebir eşleşmelerde term query kullanılır. (Mesela fatura türü DHL olanlar)
    public static Query termQuery(String field, String value) {
        return Query.of(q -> q.term(new TermQuery.Builder()
                .field(field)
                .value(value)
                .build()));
    }

    // Benzer içerik varsa match query kullanılır. (Mesela fatura d aratıp, dhl ve danzas gelmesi)
    public static Query matchQuery(String field, String value) {
        return Query.of(q -> q.match(new MatchQuery.Builder()
                .field(field)
                .query(value)
                .build()));
    }

    public static MatchQuery buildMatchQueryForFieldAndValue(String fieldName, String searchValue) {
        return new MatchQuery.Builder()
                .field(fieldName)
                .query(searchValue)
                .build();
    }
}



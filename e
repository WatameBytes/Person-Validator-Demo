{
  "size": 0,
  "query": {
    "range": {
      "@timestamp": {
        "gte": "now-15m",
        "lte": "now"
      }
    }
  },
  "aggs": {
    "all_correlation_ids": {
      "cardinality": {
        "field": "correlationId.keyword"
      }
    },
    "successful_logs": {
      "filter": {
        "bool": {
          "should": [
            { "match_phrase": { "message": "MessagePattern1" } },
            { "match_phrase": { "message": "MessagePattern2" } },
            { "match_phrase": { "message": "MessagePattern3" } }
          ],
          "minimum_should_match": 1
        }
      },
      "aggs": {
        "successful_correlation_ids": {
          "cardinality": {
            "field": "correlationId.keyword"
          }
        }
      }
    },
    "availability_ratio": {
      "bucket_script": {
        "buckets_path": {
          "success": "successful_logs>successful_correlation_ids",
          "total": "all_correlation_ids"
        },
        "script": "params.total > 0 ? params.success / params.total * 100 : 0"
      }
    }
  }
}

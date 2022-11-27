# ElasticSearch插件(ES版本7.15.1)

* [单字分词Analyzer：onecharstandard](#单字分词analyzeronecharstandard)

# 单字分词Analyzer：onecharstandard

ES的数字类型的text默认的分词器是[StandardAnalyzer](https://www.elastic.co/guide/en/elasticsearch/reference/8.5/analysis-standard-analyzer.html)，该分词器会将"123456789"（比如手机号）分词成"123456789"，所以如果查询"123"，"345"的时候，会找不到文档，但是ES的StandardAnalyzer 支持配置**max_token_length**，将该值改为1，可以实现单字分词的效果，如下[**分析文本**](#分析文本)有示例。可以应用于如根据手机号的一部分搜索文档的功能，但是每次配置比较繁琐，所以创建了onecharstandard的插件。下面演示正常配置过程：

## 创建索引

```
PUT yourindex
{
  "mappings": {
    "properties": {
      "mobile": {
        "type": "text",
        "analyzer": "custom_onecharstandard",
        "fields": {
          "keyword": {
            "type": "keyword"
          }
        }
      }
    }
  },
  "settings": {
    "analysis": {
      "analyzer": {
        "custom_onecharstandard": {
          "type": "standard",
          "max_token_length": 1
        }
      }
    }
  }
}
```

## 分析文本

```
POST _analyze
{
  "tokenizer": {
    "type": "standard",
    "max_token_length": 1
  },
  "text": "12345678"
}
```

## 写入文档

```
POST yourindex/_doc
{
  "mobile": "1"
}

POST yourindex/_doc
{
  "mobile": "2"
}

POST yourindex/_doc
{
  "mobile": "12"
}

POST yourindex/_doc
{
  "mobile": "21"
}

POST yourindex/_doc
{
  "mobile": "123"
}
```

## 搜索文档

- 可以使用手机号中的一部分（如一般常用为后四位）搜索文档。注意必须是match_phrase短语搜索，不能是match搜索。因为match搜索会查出不在mobile里面的内容。

  ```
  //match查询会查询全部。
  GET yourindex/_search
  {
    "query": {
      "match": {
        "mobile": "12"
      }
    }
  }
  
  //match_phrase查出只包含12的。分词效果是1，2，必须同时包括1,2的文档，且保证顺序不变。此时只有一个
  GET yourindex/_search
  {
    "query": {
      "match_phrase": {
        "mobile": "12"
      }
    }
  }
  
  //该效果和match_phrase类似
  GET yourindex/_search
  {
    "query": {
      "match": {
        "mobile": {
          "operator": "and",
          "query":"12"
        }
      }
    }
  }
  ```

- 因为配置了fields:keyword，也可以term做完全匹配。

```
GET yourindex/_search
{
  "query": {
    "term": {
      "mobile.keyword": "123"
    }
  }
}
```

但是这种方式需要在创建索引的时候在mapping和settings中配置analyzer，比较不方便，因此该插件提供了一个新的Analyzer：**onecharstandard**

```
//创建索引时可以直接配置，无需在settings里再构造自定义analyzer
PUT yourindex
{
  "mappings": {
    "properties": {
      "mobile": {
        "type": "text",
        "analyzer": "onecharstandard",
        "fields": {
          "keyword": {
            "type": "keyword"
          }
        }
      }
    }
  }
}
```

# 插件

[https://www.elastic.co/guide/en/elasticsearch/plugins/current/intro.html](https://www.elastic.co/guide/en/elasticsearch/plugins/current/intro.html)

## 运行插件

1. 执行 zip task。

2. 把elasticsearch-plugins-0.0.1.jar包移动到ESPlugin-es7.15.1-0.0.1.zip文件中。

3. 安装：elasticsearch-plugin.bat install file:///D:/ELK/ESPlugin-es7.15.1-0.0.1.zip

4. 重启

## 插件列表

elasticsearch-plugin.bat list

## 删除插件

elasticsearch-plugin.bat remove ESPlugin

# 参考

1. ES
官方Plugin例子：[https://github.com/elastic/elasticsearch/tree/main/plugins/examples](https://github.com/elastic/elasticsearch/tree/main/plugins/examples)

2. ES
官方Plugin文档：[https://www.elastic.co/guide/en/elasticsearch/plugins/current/intro.html](https://www.elastic.co/guide/en/elasticsearch/plugins/current/intro.html)
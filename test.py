from elasticsearch import Elasticsearch
es = Elasticsearch()

def ptoken(text):
    r = es.indices.analyze(
        index=index_name,
        body={
            "text": text
        },
    )
    print("{} => {}".format(
        text,
        " ".join([t["token"] for t in r["tokens"]])
    ))

index_name = "bsdconv"

es.indices.delete(index=index_name, ignore=[400, 404])
es.indices.create(
    index=index_name,
    ignore=400,
    body={
        "settings": {
            "analysis" : {
                "analyzer" : {
                    "default" : {
                        "tokenizer" : "icu_tokenizer",
                        "filter" : [
                            "bsdconv_zh_fuzzy_tw"
                        ]
                    }
                },
                "filter": {
                    "bsdconv_zh_fuzzy_tw": {
                        "type": "bsdconv",
                        "conversion": "UTF-16LE:ZH-FUZZY-TW:UTF-16LE"
                    }
                },
            }
        }
    }
)
ptoken("abc臺灣台湾")

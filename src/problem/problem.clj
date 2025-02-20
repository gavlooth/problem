(ns problem.problem
  (:require
    [clojure.java.data :as jdata]
    [clojure.java.data.builder :as builder])
  (:import
    (io.qdrant.client ConditionFactory)
    (io.qdrant.client QdrantClient QdrantGrpcClient)
    (io.qdrant.client WithPayloadSelectorFactory)
    (io.qdrant.client.grpc Points$Filter Points$Filter$Builder)
    (io.qdrant.client.grpc Points$ScrollPoints Points$ScrollPoints$Builder)))

(defn qdrant-client
  ([] (QdrantClient. (.build (QdrantGrpcClient/newBuilder "localhost" 6334  false))))
  ([chan] (QdrantClient. (.build (QdrantGrpcClient/newBuilder chan))))
  ([host port] (QdrantClient. (.build (QdrantGrpcClient/newBuilder host port false)))))


(defn make-scroll-points  [collection-name k v]
  (builder/to-java Points$ScrollPoints
                   Points$ScrollPoints$Builder
                   (Points$ScrollPoints/newBuilder)
                   {:collectionName collection-name
                    :filter (builder/to-java Points$Filter
                                             Points$Filter$Builder
                                             (Points$Filter/newBuilder)
                                             {:addMust (ConditionFactory/matchKeyword (name k) (str v))}
                                             {})
                    :limit 50
                    :withPayload (WithPayloadSelectorFactory/enable true)}
                   {}))



(defn -main
  "Invoke me with clojure -M -m problem.problem"
  [& _args]
  (try
    (make-scroll-points "name" :key "value")
    (catch Throwable t (println t))))

(def scroll-points (make-scroll-points "name" "key" "value"))

#_(jdata/from-java scroll-points)


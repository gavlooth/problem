(ns problem.problem
  (:require [clojure.java.data.builder :as builder])
  (:import (dev.langchain4j.model.openai OpenAiChatModel)
           (dev.langchain4j.model.openai OpenAiChatModel$OpenAiChatModelBuilder)))

(defn make-chat-model []
  (builder/to-java
    OpenAiChatModel 
    OpenAiChatModel$OpenAiChatModelBuilder
    (OpenAiChatModel/builder)
    {:baseUrl  "localhost"
     :apiKey (System/getenv "OPENAI_API_KEY")} {}))

(defn -main
  "Invoke me with clojure -M -m problem.problem"
  [& args]
 (try 
  (make-chat-model)
  (catch Throwable t (println t))))

(ns user
  (:require [cider.nrepl :refer (cider-nrepl-handler)]
            [clojure.string]
            [nrepl.server :as nrepl]))

(println "starting repl")

(defonce nrepl-server (nrepl/start-server :handler cider-nrepl-handler))

(binding [*out* (java.io.PrintWriter.
                 (java.io.FileOutputStream.
                  java.io.FileDescriptor/out) true)]
  (println "Staring nrepl server on"
           (str (clojure.string/join
                 "." (.getAddress  (.getInetAddress (:server-socket nrepl-server))))
                " port " (.getLocalPort (:server-socket nrepl-server)))))

(spit "./.nrepl-port" (:port nrepl-server))




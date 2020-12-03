(ns billfront.http-retry
  (:require [slingshot.slingshot :refer [try+ throw+]]))

(def handled-status-or-exceptions
  #{400 401 404 500 502 503 504
    java.net.SocketTimeoutException
    java.net.ConnectException})

(defn with-retries [retries f & args]
  (let [{:keys [value retry]} (try+ {:value (apply f args)}
                                    (catch Object e
                                      (if (and (pos? retries)
                                               (contains? handled-status-or-exceptions (or (:status e) (type e))))
                                        (binding [*out* *err*]
                                          (println (str "Got a " (or (:status e) (.getMessage e)) " (" (-> e type .getName) ")! " retries " retries left."))
                                          (Thread/sleep 500)
                                          {:retry true})
                                        (throw+))))]
    (if retry
      (recur (dec retries) f args)
      value)))

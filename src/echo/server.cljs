(ns echo.server
  (:require
   ["express" :as express]
   ["body-parser" :as parser]
   ["ngrok" :as ngrok]
   ["path" :as path]
   [goog.object]
   [clojure.string :as s]))

;; Switch println to use console.log
(enable-console-print!)


;; Set our variables
(def divider (s/join "" (repeat 80 "-")))


;; Business logic

(defn log-request
  [req body]
  (println (str "\n" divider))
  (println "RECEIVED:" (.toString (js/Date.)))
  (println "URL:"      (.-path req))
  (println "METHOD:"   (.-method req))
  (println "BODY:")
  (js/console.log body)
  (println (str divider "\n")))

(defn echo-request
  "
  A request handler that will parse the JSON body, log it, and returns:
  {\"status\" \"ok\"}
  "
  [req res _]
  (let [body (.-body req)]
    (log-request req body)
    (doto res
      (.set "Content-Type" "application/json")
      (.json (clj->js {:status :ok
                       :method (.-method req)
                       :body   body}))
      (.end))))


;; Private functions for starting the servers

(defn start-server
  "
  Starts a local echo server to log and return JSON requests
  - Defaults to port 8080 but can be overwritten
  "
  [port]
  (println (str "\nStarting server at http://localhost:" port))
  (js/Promise.
   (fn [resolve reject]
     (let [app (express)]
       (doto app
         (.use (.json parser))
         (.all "/*" echo-request)
         (.on "error" #(reject %))
         (.listen port #(resolve app)))))))

(defn start-ngrok
  "
  Starts a ngrok tunnel between the local echo server and the web allowing
  external services to send requests to your local server.
  "
  [port is-docker]
  (println (str "\nCreating public ngrok tunnel"))
  (-> ngrok
      (.connect #js {:addr port
                     :configPath (when is-docker
                                   (.join path
                                          (.cwd js/process)
                                          "./ngrok.yml"))})
      (.then #(println (str "  -> tunnel at " %)))
      (.then #(.getUrl ngrok))
      (.then #(println (str "  -> inspector " %)))
      (.then #(println "  -> ready"))))


;; Initialize the servers

(defn -main
  [port & args]
  (let [port (or port 8080)
        is-docker (contains? (set args) "--docker")]
    (-> (start-server port)
        (.then #(println "  -> ready"))
        (.then #(start-ngrok port is-docker)))))

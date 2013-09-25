(defproject cloudy-peasant "0.1.0-SNAPSHOT"
  :description "Clojure code that I haven't bothered to extract into proper libraries."
  :url "http://github.com/brokensandals/cloudy-peasant"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clj-http "0.7.6"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.4"]
                 [org.clojure/clojure "1.5.1"]
                 [org.clojure/data.json "0.2.3"]
                 [org.clojure/tools.namespace "0.2.4"]
                 [ring/ring-core "1.2.0"]
                 [ring/ring-devel "1.2.0"]
                 [ring/ring-jetty-adapter "1.2.0"]
                 [watchtower "0.1.1"]]
  :repl-options {:init (eval '(do (use 'misc.autoload) (auto-use (find-ns 'user) "src")))})

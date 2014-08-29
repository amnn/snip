(defproject snip "0.1.0-SNAPSHOT"
  :description
  ("Cut up a source file into snippets so you can insert "
   "them into a report/thesis/article or other publication "
   "of some sort")
  :url "https://github.com/asQuirreL/snip"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.cli "0.3.1"]]
  :main ^:skip-aot snip.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

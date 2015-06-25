(defproject fleet "0.10.2"
  :description "Templating System for Clojure"
  :url "http://github.com/Flamefork/fleet"
  :scm {:name "git"
        :url "https://github.com/Flamefork/fleet"}
  :license {:name "MIT License"
            :url "http://www.opensource.org/licenses/mit-license.php"}
  :signing {:gpg-key "ilia@flamefork.ru"}

  :dependencies [[org.clojure/clojure "1.4.0"]]
  :global-vars {*warn-on-reflection* true}
  :java-source-paths ["src"])

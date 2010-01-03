(ns ru.flamefork.fleet
  (:use clojure.test)
  (:use clojure.contrib.pprint))

(deftest basics
  (pprint (fleet
"<p><(post :body)></p>
<ul>
  <(for [tag (post :tags)] \">
    <li><(tag)></li>
  <\")>
</ul>")))

(deftest handmade-compilation
  (println
    ((fn [post]
      (apply str ["<p>" (apply str (post :body)) "</p>\n<ul>\n"
                  (apply str (for [tag (post :tags)]
                    (apply str ["<li>" (apply str (str tag)) "</li>\n"])))
                  "</ul>"])
      ) {:body "Body" :tags ["tag1" "tag2" "tag3"]})))
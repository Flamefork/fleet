(do
  (clojure.core/use (quote ru.flamefork.fleet.runtime))
  (clojure.core/fn [post] (ru.flamefork.fleet.runtime/screen str [
    (ru.flamefork.fleet.runtime/raw "<p>")
    (ru.flamefork.fleet.runtime/screen str (raw (post :body)))
    (ru.flamefork.fleet.runtime/raw "</p>\n<ul>\n  ")
    (ru.flamefork.fleet.runtime/screen str (for [tag (post :tags)]
      (ru.flamefork.fleet.runtime/screen str [
        (ru.flamefork.fleet.runtime/raw "\n    <li>")
        (ru.flamefork.fleet.runtime/screen str (str tag))
        (ru.flamefork.fleet.runtime/raw "</li>\n  ")])))
    (ru.flamefork.fleet.runtime/raw "\n</ul>")])))

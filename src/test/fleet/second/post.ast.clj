[:tpl (
  [:text "<p>"]
  [:embed (
    [:text "post :body"])]
  [:text "</p>\n<ul>\n  "]
  [:embed (
    [:text "for [tag (post :tags)] "]
    [:tpl (
      [:text "\n    <li>"]
      [:embed (
        [:text "str tag"])]
      [:text "</li>\n  "])])]
  [:text "\n</ul>"])]
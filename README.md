# Fleet

Fleet is a FLExiblE Templates for Clojure.

## Status

Desinging language and API right now here in readme file. There is no implementation yet, but
I make decisions not just dreaming of language features :) but trying to keep in mind implementation possibility and complexity.

## API

`(fleet template-str)`  
Creates anonymous function from template containing in template-str.
Local bindings will be available in this templates.

`(deftemplate fn-name [params*] source?)`  
Creates function with name fn-name and defined params (just a convinient way to set local bindings).  
If source is defined, and it's String, it's parsed.  
If source is defined, and it's Reader, it's read and parsed.  
If source is not defined, file `fn_name.fleet` found in one of `*fleet-template-paths*` is loaded and parsed.

`*fleet-template-paths*`  
List of path used for template search.

## Template Language

Main Fleet construction is Spaceship `<()>`, just cause (star)fleet consists of many spaceships.

`<()>` is almost equivalent to `()`, so
`<h1><(body)></h1>` in Fleet is nearly the same as `(str "<h1>" (body) "</h1>")` in Clojure.

The only difference is that `(body)` output gets html-encoded to prevent XSS.
Use `raw` function to prevent encoding: `<(raw "<br/>")>`.
Use `str` function to place value `<(str posts-count)>`.

This seems to be complete system, but writing something like
    <(raw (for [p posts]
      (fleet "<li class=\"post\"><(p :title)></li>")))>
is too ugly.. And defining `<li class="post"><(p :title)></li>` as separate template
can be overkill in many cases. So there should be the way of defining anonymous templates and applying them inplace.

Slipway construction `"><"` intended for defining anonymous template and applying it.
The previous example could be rewritten using Slipway as
    <(for [p posts] ">
      <li class="post"><(p :title)></li>
    <")>

This example has two points worth mentioning.
Result of `"><"` is String, not anonymous template.
Also Spaceship with Slipway attached concidered `raw` by default.

## Examples

Template file (`post_dedicated.fleet`):
    <head>
      <title><(post :title)></title>

      <(stylesheet :main)>
      <(raw "<script>alert('Hello!')</script>")>
    </head>
    <body>
    
    <p><(str notice)></p>
    
    <(comment Begin of post)>
    <(inside-frame (let [p post] ">
      Author: <(p :author)><br/>
      Date: <(p :date)><br/>
    <"))>

    <p><(post :body)></p>
    <ul>
      <(for [tag (post :tags] ">
        <li><(tag)></li>
      <")>
    </ul>
    <(comment End of post)>

    <(footer)>
    </body>
    </html>
Clojure:
    (def footer (fleet "<p>&copy; <(.get (Calendar/getInstance) Calendar/YEAR)> Flamefork</p>"))

    (deftemplate post-page [post] "post_dedicated")
    
    (footer)

    (post-page p)

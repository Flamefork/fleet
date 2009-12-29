# Fleet

Fleet is a FLExiblE Templates system for Clojure.

## Status

Desinging language and API right now here in readme file. So there is no implementation yet.
I make decisions not just dreaming of language features :) but keeping in mind implementation possibility and complexity.

## API

`(fleet template-str)`  
Creates anonymous function from template containing in template-str.
Local bindings will be available in this templates.

`(deftemplate fn-name [params*] source?)`  
Creates function with name fn-name defined params.
Here, params are just a convinient way to set local bindings.
If source is defined, and it's String, it's parsed.
If source is defined, and it's Reader, it's read and parsed.
If source is not defined, file `fn_name.fleet` found in one of `*fleet-template-paths*` is loaded and parsed.

`*fleet-template-paths*`  
List of dirs used for template search.

## Template Language

Main Fleet construction is Spaceship `<()>`, just cause (star)fleet consists of many spaceships.

`<()>` is almost equivalent to `()`, so
`<h1><(body)></h1>` in Fleet is nearly the same as `(str "<h1>" (body) "</h1>")` in Clojure.

The only difference is that `(body)` output gets html-encoded to prevent XSS.
Use `raw` function to prevent encoding: `<(raw "<br/>")>`.

This seems to be complete system, but writing something like
    <(raw (for [p posts]
      (fleet "<li class=\"post\"><(p :title)></li>")))>
is too ugly.. And defining `<li class="post"><(p :title)></li>` as separate template
can be overkill in many cases. So there should be the way of defining anonymous templates and applying them.

Slipway construction `"><"` intended for defining anonymous template and applying it.
The previous example could be rewritten using Slipway as
    <(for [p posts] ">
      <li class="post"><(p :title)></li>
    <")>

This example has two points worth mentioning.
Result of `"><"` is String and therefore is equivalent to `""` in Clojure.
Also Spaceship with Slipway attached concidered `raw` by default.

## Examples

Template file (`post_dedicated.fleet`):
<script src="http://gist.github.com/265042.js"></script>
Clojure:
<script src="http://gist.github.com/265043.js"></script>

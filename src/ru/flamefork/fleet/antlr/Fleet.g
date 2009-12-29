grammar Fleet;

@lexer::header {
    package ru.flamefork.fleet.antlr;
}

@header {
    package ru.flamefork.fleet.antlr;
}

@lexer::members {
    boolean em = false;
}

input:
	text? (embed text?)* ;

embed:
	EM_OPEN text? EM_CLOSE;

text:
	(options{greedy=false;}: CHAR)+;

EM_OPEN:
	{ !em }?=>
	(SPACESHIP_OPEN | SLIPWAY_CLOSE)
	{ em = true; };
	
EM_CLOSE:
	{ em }?=>
	(SPACESHIP_CLOSE | SLIPWAY_OPEN)
	{ em = false; };

fragment SPACESHIP_OPEN:  '<(';
fragment SPACESHIP_CLOSE: ')>';
fragment SLIPWAY_OPEN:  '">';
fragment SLIPWAY_CLOSE: '<"';

CHAR: .;

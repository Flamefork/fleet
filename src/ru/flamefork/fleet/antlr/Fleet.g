grammar Fleet;

options {
	output = AST;
}

@lexer::header {
	package ru.flamefork.fleet.antlr;
}

@header {
	package ru.flamefork.fleet.antlr;
}

@lexer::members {
	// Embed mode
	boolean em = false;
}

input:
	template;

template:
	text? (spaceship text?)*;

embed:
	text? (slipway text?)*;

slipway:
	SLIPWAY_OPEN^ template SLIPWAY_CLOSE!;

spaceship:
	SPACESHIP_OPEN^ embed SPACESHIP_CLOSE!;

text:
	(options{greedy=false;}: CHAR)+;

SPACESHIP_OPEN:
	{ !em }?=>
	'<('
	{ em = true; };
	
SPACESHIP_CLOSE:
	{ em }?=>
	')>'
	{ em = false; };

SLIPWAY_OPEN:
	{ em }?=>
	'">'
	{ em = false; };

SLIPWAY_CLOSE:
	{ !em }?=>
	'<"'
	{ em = true; };
	
CHAR: .;

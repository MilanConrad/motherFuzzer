grammar Acola;
brain : SPACE* 'brain' SPACE* '"' SPACE* IDENTIFIER SPACE* '"' SPACE* '{' SPACE* '\n' SPACE* (SPACE* instruction '\n')+ SPACE* '}' EOF;
instruction : mark
              |unmark
              |turn
              |move
              |sense
              |set
              |unset
              |pickup
              |drop
              |jump
              |flip
              |sensemarker
              |direction
              |test
              |breed
              |COMMENT;

mark: 'mark' SPACE* (MARKER|REGISTER);
unmark: 'unmark' SPACE* (MARKER|REGISTER);
turn: 'turn' SPACE* ('left' | 'right');
move: 'move' SPACE* 'else' SPACE* (NUMBER|MARKER|REGISTER);
sense: 'sense' SPACE* FIELD SPACE* TARGET SPACE* 'else' SPACE* (NUMBER|MARKER|REGISTER);
sensemarker: 'sense' SPACE* FIELD SPACE* 'marker' SPACE* (MARKER|REGISTER) SPACE* 'else' SPACE* (NUMBER|MARKER|REGISTER);
set: 'set' SPACE* REGISTER;
unset: 'unset' SPACE* REGISTER;
pickup: 'pickup' SPACE* 'else' SPACE* (NUMBER|MARKER|REGISTER);
drop: 'drop' SPACE* 'else' SPACE* (NUMBER|MARKER|REGISTER);
jump: 'jump' SPACE* (NUMBER|MARKER|REGISTER);
flip: 'flip' SPACE* (NUMBER|MARKER|REGISTER) SPACE* 'else' SPACE* (NUMBER|MARKER|REGISTER);
test: 'test' SPACE* (NUMBER|MARKER|REGISTER) SPACE* 'else' SPACE* (NUMBER|MARKER|REGISTER);
direction: 'direction' SPACE* DIRECTION SPACE* 'else' SPACE* (NUMBER|MARKER|REGISTER);
breed: 'breed' SPACE* 'else' SPACE* (NUMBER|MARKER|REGISTER);

FIELD : 'here' | 'ahead' | 'left' | 'right';
TARGET : 'foe' | 'foehome' | 'friend' | 'food' | 'antlion' | 'rock' | 'foefood' | 'foemarker' | 'home' | 'friendfood';
DIRECTION :  'northwest' | 'west' | 'southwest' | 'southeast' | 'east' | 'northeast';
REGISTER : [0-5];
MARKER : [0-6];
NUMBER : [0-9]+;
IDENTIFIER : [a-zA-Z_.-][a-zA-Z0-9_.-]*;
COMMENT : ('/*' .*? '*/' | '//' .*? '\n') -> skip;
SPACE : ([ ]+);

grammar Acola;

@parser::members
{
  @Override
  public void notifyErrorListeners(Token offendingToken, String msg, RecognitionException ex)
  {
    throw new RuntimeException(msg);
  }
}

@lexer::members
{
  @Override
  public void recover(RecognitionException ex)
  {
    throw new RuntimeException(ex.getMessage());
  }
}

brain : 'brain' (COMMENT|SPACE|NEWLINE)* '"' IDENTIFIER '"' (COMMENT|SPACE|NEWLINE)* '{' ((COMMENT|SPACE|NEWLINE)* instruction (COMMENT|SPACE)* NEWLINE)+  '}' NEWLINE* EOF;
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
              |breed;

sensemarker: 'sense'     (COMMENT*|SPACE*) ('here'|'ahead'|'left'|'right') (COMMENT*|SPACE*) 'marker' (COMMENT*|SPACE*) (MARKER|REGISTER) (COMMENT*|SPACE*) 'else' (COMMENT*|SPACE*) ALLNUMS (COMMENT*|SPACE*) ;
sense:       'sense'     (COMMENT*|SPACE*) ('here'|'ahead'|'left'|'right') (COMMENT*|SPACE*) TARGET   (COMMENT*|SPACE*)                                     'else' (COMMENT*|SPACE*) ALLNUMS (COMMENT*|SPACE*) ;
flip:        'flip'      (COMMENT*|SPACE*) ALLNUMS                         (COMMENT*|SPACE*)                                                                'else' (COMMENT*|SPACE*) ALLNUMS (COMMENT*|SPACE*) ;
test:        'test'      (COMMENT*|SPACE*) ALLNUMS                         (COMMENT*|SPACE*)                                                                'else' (COMMENT*|SPACE*) ALLNUMS (COMMENT*|SPACE*) ;
direction:   'direction' (COMMENT*|SPACE*) DIRECTION                       (COMMENT*|SPACE*)                                                                'else' (COMMENT*|SPACE*) ALLNUMS                   ;
pickup:      'pickup'    (COMMENT*|SPACE*)                                                                                                                  'else' (COMMENT*|SPACE*) ALLNUMS (COMMENT*|SPACE*) ;
drop:        'drop'      (COMMENT*|SPACE*)                                                                                                                  'else' (COMMENT*|SPACE*) ALLNUMS (COMMENT*|SPACE*) ;
move:        'move'      (COMMENT*|SPACE*)                                                                                                                  'else' (COMMENT*|SPACE*) ALLNUMS (COMMENT*|SPACE*) ;
breed:       'breed'     (COMMENT*|SPACE*)                                                                                                                  'else' (COMMENT*|SPACE*) ALLNUMS (COMMENT*|SPACE*) ;
turn:        'turn'      (COMMENT*|SPACE*) ('left'|'right')                (COMMENT*|SPACE*)                                                                                                                   ;
mark:        'mark'      (COMMENT*|SPACE*) (MARKER|REGISTER)               (COMMENT*|SPACE*)                                                                                                                   ;
unmark:      'unmark'    (COMMENT*|SPACE*) (MARKER|REGISTER)               (COMMENT*|SPACE*)                                                                                                                   ;
set:         'set'       (COMMENT*|SPACE*) REGISTER                        (COMMENT*|SPACE*)                                                                                                                   ;
unset:       'unset'     (COMMENT*|SPACE*) REGISTER                        (COMMENT*|SPACE*)                                                                                                                   ;
jump:        'jump'      (COMMENT*|SPACE*) ALLNUMS                         (COMMENT*|SPACE*)                                                                                                                   ;

TARGET : 'foe' | 'foehome' | 'friend' | 'food' | 'antlion' | 'rock' | 'foefood' | 'foemarker' | 'home' | 'friendfood';
DIRECTION :  'northwest' | 'west' | 'southwest' | 'southeast' | 'east' | 'northeast';
REGISTER : [0-5];
MARKER : [0-6];
NUMBER : [0-9]+;
IDENTIFIER : [a-zA-Z_.-][a-zA-Z0-9_.-]+;
COMMENT : ('/*' .*? '*/' | '//' .*? ('\n'|'\r'|'\\n'|'\\r')) {setText(" ");};
SPACE : ([ ]|[	]);
NEWLINE : ('\n'|'\r'|'\\n'|'\\r'|'\\u'[0-9][0-9][0-9][0-9]);
ALLNUMS : (NUMBER|MARKER|REGISTER);

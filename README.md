## Commands to run the application with Docker
```
docker build -t knight_board:latest .
docker run \
-e BOARD_API={board-api-link}/board.json \
-e COMMANDS_API={commands-api-link}/commands.json \
knight_board:latest
```


## Board API
The response of the board API is:
```json
{
    "width": 10, //Dimension of the board on x axis
    "height": 10, //Dimension of the board on y axis
    "obstacles": [ //List of points of the board where the knight cannot step on
        { 
            "x": 1,
            "y": 1,
        },
        {
            "x": 1,
            "y": 2
        }
    ]
}
```

## Command API
The response of the API is:
```json
{
    "commands": [ 
        "START 1,0,NORTH",
        "ROTATE SOUTH",
        "MOVE 3",
        "ROTATE EAST",
        "MOVE 5"
    ]
}
```

### Commands definition
The king will tell his knight how to move on the board. Here a list of the command that the knight must be aware of:

* `START X,Y,DIR`: This is always the first command received by the knight. `X,Y` represent the starting position of the knight, `DIR` represent the direction is facing. `DIR` could be NORTH, SOUTH, EST, WEST.
* `ROTATE DIR`: This command made the knight turn itself facing the desired direction. `DIR` could be NORTH, SOUTH, EST, WEST. The kningt does not change his position when this command is executed.
* `MOVE N`: The knight will move by `N` steps forward (`N>=0`).

## Expected output
The program should output on standard output (console) a json representing the outcome of the commands execution:
```json
{
    "position": {
        "x": 1,
        "y": 1,
        "direction": "NORTH"
    },
    "status": "SUCCESS"
}
```

### Status definition
* `SUCCESS`: when the whole list of commands has been executed without any error
* `INVALID_START_POSITION`: when the starting position is not valid
* `OUT_OF_THE_BOARD`: then the knight fell out of the board while executing the given commands
* `GENERIC_ERROR`: for all other possible error cases

from random import shuffle

def generateElements():
    elements = list(range(1,17))  # a list from 0 to 6. list() not needed in Python 2
    shuffle(elements)
    return elements

def createBoard():
    board = [[0,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,0]]
    numbersForTheBoard = generateElements()
    for row in range(4):
        for column in range(4):
            board[row][column] = numbersForTheBoard.pop(0)
        print(board[row])

createBoard()
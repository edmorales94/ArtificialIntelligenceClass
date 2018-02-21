class slidePuzzle:
    direction = {"LEFT": "l", "RIGHT": "r", "UP":"u", "DOWN": "d"}
    board = []
    path = []
    solvePuzzleUsingMethod = ""
    lastMove = ""
    
    def __init__(self):
        for row in range(4):
            self.board.append([])
            for col in range(4):
                if row == 3 and col == 3:
                   self.board[row].append(0)

                else:
                   self.board[row].append(4 * row + col + 1)


    def __repr__(self):
        string = ""
        for row in self.board:
            for number in row:
                if len(str(number)) == 1:
                   string += "  " + str(number)
                elif len(str(number)) > 1:
                   string += " " + str(number)
            string += "\n"
        return string

def main():
    board = slidePuzzle()
    print(board)

main()

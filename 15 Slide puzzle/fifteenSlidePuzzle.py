class slidePuzzle:
    direction = {"LEFT": "l", "RIGHT": "r", "UP":"u", "DOWN": "d"}
    board = []
    path = []
    solvePuzzleUsingMethod = ""
    lastMove = ""

    """---------- constructor -----------------------------------------------"""
    def __init__(self):
        for row in range(4):
            self.board.append([])
            for col in range(4):
                if row == 3 and col == 3:
                   self.board[row].append(0)

                else:
                   self.board[row].append(4 * row + col + 1)

    """---------- print board method ----------------------------------------"""
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
    

    """---------- get blank space postion method ----------------------------"""
    def get_blank_space_position(self):
        for i in range(4):
            for j in range(4):
                if self.board[i][j] == 0:
                    print("Black space location: ", [i,j])
                    return [i,j]
                

"""---------- main ----------------------------------------------------------"""
def main():
    board = slidePuzzle()
    board.get_blank_space_position()
    print(board)

main()

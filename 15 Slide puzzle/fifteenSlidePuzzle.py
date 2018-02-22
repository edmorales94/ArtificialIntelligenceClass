import math


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
                if self.board[i][j] == 16:
                    print("Black space location: ", [i,j])
                    return [i,j]
                

    """---------- swap elements method --------------------------------------"""
    def swap_items(self, row1, column1, row2, column2):
        temp = self.board[row1][column1]
        self.board[row1][column1] = self.board[row2][column2]
        self.board[row2][column2] = temp


    """---------- get move method -------------------------------------------"""
    def where_to_move_blank_space(self, piece_to_move):
        blank_position = self.get_blank_space_position()
        row = blank_position[0]
        column = blank_position[1]
        if row > 0 and piece_to_move == self.board[row-1][column]:
            return self.direction["UP"]
        elif column < 3 and piece_to_move == self.board[row][column +1]:
            return self.direction["RIGHT"]
        elif row < 3 and piece_to_move == self.board[row + 1][column]:
            return self.direction["DOWN"]
        elif column > 0 and piece_to_move == self.board[row][column -1]:
            return self.direction["LEFT"]


    """---------- move blank space method -----------------------------------"""
    def move(self, piece_to_move):
        move = self.where_to_move__blank_space(piece_to_move)

        if not move is None:
            blank_space_position = self.get_blank_space_position()
            row = blank_space_position[0]
            column = blank_space_position[1]
            if move == "u":
                self.swap_items(row,column,row-1,column)
            elif move == "r":
                self.swap_items(row,column,row, column+1)
            elif move == "d":
                self.swap_items(row, column, row+1, column)
            elif move == "l":
                self.swap_items(row, column, row, column-1)


            self.lastMove = piece_to_move
            return move

    """---------- is goal state method --------------------------------------"""
    def is_goal_state_reached(self):
        for row in range(4):
            for column in range(4):
                piece = self.board[row][column]
                if piece != 16:
                    original_row = math.floor((piece-1)/4)
                    orignal_column = (piece-1)%4
                    if row != original_row or column != original_column:
                        return False
        return True

    
        
        
"""---------- main ----------------------------------------------------------"""
def main():
    board = slidePuzzle()
    board.get_blank_space_position()
    print(board)

main()

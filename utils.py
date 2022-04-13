import numpy as np
from mat_operator import MatSign

def print_fut_matrix(fut_matrix, constr_dict):
    print_arr = np.full((fut_matrix.shape[0] * 2, fut_matrix.shape[1] * 2), ' ')
    for row_idx in range(0, fut_matrix.shape[1] * 2):
        for col_idx in range(0, fut_matrix.shape[0] * 2):
            if row_idx % 2 == 0 and col_idx % 2 == 0:
                print_arr[row_idx][col_idx] = fut_matrix[row_idx // 2][col_idx // 2]
            
    for key in constr_dict.keys():
        for tup in constr_dict[key]:
            if key < tup[1]:
                if key + 1 == tup[1] and tup[0] == MatSign.LESS:
                    row_idx = (key // fut_matrix.shape[0]) * 2
                    col_idx = (key % fut_matrix.shape[1]) * 2
                    print_arr[row_idx][col_idx + 1] = '<'
                elif key + 1 == tup[1] and tup[0] == MatSign.MORE:
                    row_idx = (key // fut_matrix.shape[0]) * 2
                    col_idx = (key % fut_matrix.shape[1]) * 2
                    print_arr[row_idx][col_idx + 1] = '>'
                elif key + fut_matrix.shape[1] == tup[1] and tup[0] == MatSign.MORE:
                    row_idx = (key // fut_matrix.shape[0]) * 2
                    col_idx = (key % fut_matrix.shape[1]) * 2
                    print_arr[row_idx + 1][col_idx] = 'v'
                elif key + fut_matrix.shape[1] == tup[1] and tup[0] == MatSign.LESS:
                    row_idx = (key // fut_matrix.shape[0]) * 2
                    col_idx = (key % fut_matrix.shape[1]) * 2
                    print_arr[row_idx + 1][col_idx] = '^'


    print(print_arr)


def free_spaces(bin_matrix):
    free_cords_list = []
    for i in range(0, bin_matrix.shape[0]):
        for j in range(0, bin_matrix.shape[1]):
            if bin_matrix[i][j] == -1:
                free_cords_list.append((i,j))
    return free_cords_list
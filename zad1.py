import numpy as np
from binary import binary_solver_bt, binary_solver_fc, list_of_solutions_bin, binary_solver_bt_ran_pick, binary_solver_fc_ran_pick
from futoshiki import futoshiki_solver_bt, list_of_solutions_ft
from utils import print_fut_matrix, free_spaces
import mat_operator

def binary_6_6_bt():
    #global list_of_solutions_bin
    bin_matrix = np.full((6,6), -1, int)
    with open('binary_6x6') as f:
        lines = f.readlines()
    for i in range(len(lines)):
        for j in range(len(lines[i])):
            if lines[i][j] == '1':
                bin_matrix[i][j] = 1
            elif lines[i][j] == '0':
                bin_matrix[i][j] = 0
    #print(bin_matrix)
    #print(free_spaces(bin_matrix))
    binary_solver_bt(bin_matrix, (0,0))
    #binary_solver_bt_ran_pick(bin_matrix, free_spaces(bin_matrix))
    print(list_of_solutions_bin)

def binary_6_6_fc():
    #global list_of_solutions_bin
    bin_matrix = np.full((6,6), -1, int)
    with open('binary_6x6') as f:
        lines = f.readlines()
    for i in range(len(lines)):
        for j in range(len(lines[i])):
            if lines[i][j] == '1':
                bin_matrix[i][j] = 1
            elif lines[i][j] == '0':
                bin_matrix[i][j] = 0
    #print(bin_matrix)
    binary_solver_fc(bin_matrix, (0,0), 3)
    #binary_solver_fc_ran_pick(bin_matrix, free_spaces(bin_matrix), 4)
    print(list_of_solutions_bin)

    
def binary_8_8_bt():
    bin_matrix = np.full((8,8), -1, int)
    with open('binary_8x8') as f:
        lines = f.readlines()
    for i in range(len(lines)):
        for j in range(len(lines[i])):
            if lines[i][j] == '1':
                bin_matrix[i][j] = 1
            elif lines[i][j] == '0':
                bin_matrix[i][j] = 0
    #print(bin_matrix)
    binary_solver_bt(bin_matrix, (0,0))
    #binary_solver_bt_ran_pick(bin_matrix, free_spaces(bin_matrix))
    print(list_of_solutions_bin)

def binary_8_8_fc():
    bin_matrix = np.full((8,8), -1, int)
    with open('binary_8x8') as f:
        lines = f.readlines()
    for i in range(len(lines)):
        for j in range(len(lines[i])):
            if lines[i][j] == '1':
                bin_matrix[i][j] = 1
            elif lines[i][j] == '0':
                bin_matrix[i][j] = 0
    #print(bin_matrix)
    binary_solver_fc(bin_matrix, (0,0), 6)
    print(list_of_solutions_bin)

def binary_10_10_bt():
    bin_matrix = np.full((10,10), -1, int)
    with open('binary_10x10') as f:
        lines = f.readlines()
    for i in range(len(lines)):
        for j in range(len(lines[i])):
            if lines[i][j] == '1':
                bin_matrix[i][j] = 1
            elif lines[i][j] == '0':
                bin_matrix[i][j] = 0
    #print(bin_matrix)
    binary_solver_bt(bin_matrix, (0,0))
    #binary_solver_bt_ran_pick(bin_matrix, free_spaces(bin_matrix))
    print(list_of_solutions_bin)
    #comparison = list_of_solutions[0] == list_of_solutions[1]
    #equal_arrays = comparison.all()
    #print(comparison)

def binary_10_10_fc():
    bin_matrix = np.full((10,10), -1, int)
    with open('binary_10x10') as f:
        lines = f.readlines()
    for i in range(len(lines)):
        for j in range(len(lines[i])):
            if lines[i][j] == '1':
                bin_matrix[i][j] = 1
            elif lines[i][j] == '0':
                bin_matrix[i][j] = 0
    #print(bin_matrix)
    binary_solver_bt(bin_matrix, (0,0))
    print(list_of_solutions_bin)
    #comparison = list_of_solutions[0] == list_of_solutions[1]
    #equal_arrays = comparison.all()
    #print(comparison)

def futoshiki_4_4():
    fut_matrix = np.full((4,4), -1, int)
    constr_dict = {}
    with open('futoshiki_4x4') as f:
        lines = f.readlines()
    for str_idx in range(len(lines)):
        lines[str_idx] = lines[str_idx].strip()

    #print(lines) 
    for i in range(0, 4):
        for j in range(0, 4):
            constr_dict[j * 4 + i] = []

    for i in range(len(lines)):
        if i % 2 == 0:
            for j in range(len(lines[i])):
                if lines[i][j] == '>':
                    fst_elem_x = (j - 1) // 2
                    fst_elem_y = i // 2
                    snd_elem_x = (j + 1) // 2
                    snd_elem_y = i // 2
                    constr_dict[fst_elem_y * 4 + fst_elem_x].append((mat_operator.MatSign.MORE, snd_elem_y * 4 + snd_elem_x))
                    constr_dict[snd_elem_y * 4 + snd_elem_x].append((mat_operator.MatSign.LESS, fst_elem_y * 4 + fst_elem_x))
                elif lines[i][j] == '<':
                    fst_elem_x = (j - 1) // 2
                    fst_elem_y = i // 2
                    snd_elem_x = (j + 1) // 2
                    snd_elem_y = i // 2
                    constr_dict[fst_elem_y * 4 + fst_elem_x].append((mat_operator.MatSign.LESS, snd_elem_y * 4 + snd_elem_x))
                    constr_dict[snd_elem_y * 4 + snd_elem_x].append((mat_operator.MatSign.MORE, fst_elem_y * 4 + fst_elem_x))
                elif lines[i][j] != '-' and lines[i][j] != 'x':
                    fut_matrix[i // 2][j // 2] = int(lines[i][j])
        else:
            for j in range(len(lines[i])):
                if lines[i][j] == '>':
                    fst_elem_x = j
                    fst_elem_y = (i - 1) // 2
                    snd_elem_x = j
                    snd_elem_y = (i + 1) // 2
                    constr_dict[fst_elem_y * 4 + fst_elem_x].append((mat_operator.MatSign.MORE, snd_elem_y * 4 + snd_elem_x))
                    constr_dict[snd_elem_y * 4 + snd_elem_x].append((mat_operator.MatSign.LESS, fst_elem_y * 4 + fst_elem_x))
                elif lines[i][j] == '<':
                    fst_elem_x = j
                    fst_elem_y = (i - 1) // 2
                    snd_elem_x = j
                    snd_elem_y = (i + 1) // 2
                    constr_dict[fst_elem_y * 4 + fst_elem_x].append((mat_operator.MatSign.LESS, snd_elem_y * 4 + snd_elem_x))
                    constr_dict[snd_elem_y * 4 + snd_elem_x].append((mat_operator.MatSign.MORE, fst_elem_y * 4 + fst_elem_x))

    #print(fut_matrix)
    #print(constr_dict)
    futoshiki_solver_bt(fut_matrix, (0,0), constr_dict)
    print_fut_matrix(list_of_solutions_ft[0], constr_dict)


def futoshiki_5_5():
    fut_matrix = np.full((5,5), -1, int)
    constr_dict = {}
    with open('futoshiki_5x5') as f:
        lines = f.readlines()
    for str_idx in range(len(lines)):
        lines[str_idx] = lines[str_idx].strip()

    #print(lines) 
    for i in range(0, 5):
        for j in range(0, 5):
            constr_dict[j * 5 + i] = []

    for i in range(len(lines)):
        if i % 2 == 0:
            for j in range(len(lines[i])):
                if lines[i][j] == '>':
                    fst_elem_x = (j - 1) // 2
                    fst_elem_y = i // 2
                    snd_elem_x = (j + 1) // 2
                    snd_elem_y = i // 2
                    constr_dict[fst_elem_y * 5 + fst_elem_x].append((mat_operator.MatSign.MORE, snd_elem_y * 5 + snd_elem_x))
                    constr_dict[snd_elem_y * 5 + snd_elem_x].append((mat_operator.MatSign.LESS, fst_elem_y * 5 + fst_elem_x))
                elif lines[i][j] == '<':
                    fst_elem_x = (j - 1) // 2
                    fst_elem_y = i // 2
                    snd_elem_x = (j + 1) // 2
                    snd_elem_y = i // 2
                    constr_dict[fst_elem_y * 5 + fst_elem_x].append((mat_operator.MatSign.LESS, snd_elem_y * 5 + snd_elem_x))
                    constr_dict[snd_elem_y * 5 + snd_elem_x].append((mat_operator.MatSign.MORE, fst_elem_y * 5 + fst_elem_x))
                elif lines[i][j] != '-' and lines[i][j] != 'x':
                    fut_matrix[i // 2][j // 2] = int(lines[i][j])
        else:
            for j in range(len(lines[i])):
                if lines[i][j] == '>':
                    fst_elem_x = j
                    fst_elem_y = (i - 1) // 2
                    snd_elem_x = j
                    snd_elem_y = (i + 1) // 2
                    constr_dict[fst_elem_y * 5 + fst_elem_x].append((mat_operator.MatSign.MORE, snd_elem_y * 5 + snd_elem_x))
                    constr_dict[snd_elem_y * 5 + snd_elem_x].append((mat_operator.MatSign.LESS, fst_elem_y * 5 + fst_elem_x))
                elif lines[i][j] == '<':
                    fst_elem_x = j
                    fst_elem_y = (i - 1) // 2
                    snd_elem_x = j
                    snd_elem_y = (i + 1) // 2
                    constr_dict[fst_elem_y * 5 + fst_elem_x].append((mat_operator.MatSign.LESS, snd_elem_y * 5 + snd_elem_x))
                    constr_dict[snd_elem_y * 5 + snd_elem_x].append((mat_operator.MatSign.MORE, fst_elem_y * 5 + fst_elem_x))

    #print(fut_matrix)
    #print(constr_dict)
    futoshiki_solver_bt(fut_matrix, (0,0), constr_dict)
    print_fut_matrix(list_of_solutions_ft[0], constr_dict)

def futoshiki_6_6():
    fut_matrix = np.full((6,6), -1, int)
    constr_dict = {}
    with open('futoshiki_6x6') as f:
        lines = f.readlines()
    for str_idx in range(len(lines)):
        lines[str_idx] = lines[str_idx].strip()

    #print(lines) 
    for i in range(0, 6):
        for j in range(0, 6):
            constr_dict[j * 6 + i] = []

    for i in range(len(lines)):
        if i % 2 == 0:
            for j in range(len(lines[i])):
                if lines[i][j] == '>':
                    fst_elem_x = (j - 1) // 2
                    fst_elem_y = i // 2
                    snd_elem_x = (j + 1) // 2
                    snd_elem_y = i // 2
                    constr_dict[fst_elem_y * 6 + fst_elem_x].append((mat_operator.MatSign.MORE, snd_elem_y * 6 + snd_elem_x))
                    constr_dict[snd_elem_y * 6 + snd_elem_x].append((mat_operator.MatSign.LESS, fst_elem_y * 6 + fst_elem_x))
                elif lines[i][j] == '<':
                    fst_elem_x = (j - 1) // 2
                    fst_elem_y = i // 2
                    snd_elem_x = (j + 1) // 2
                    snd_elem_y = i // 2
                    constr_dict[fst_elem_y * 6 + fst_elem_x].append((mat_operator.MatSign.LESS, snd_elem_y * 6 + snd_elem_x))
                    constr_dict[snd_elem_y * 6 + snd_elem_x].append((mat_operator.MatSign.MORE, fst_elem_y * 6 + fst_elem_x))
                elif lines[i][j] != '-' and lines[i][j] != 'x':
                    fut_matrix[i // 2][j // 2] = int(lines[i][j])
        else:
            for j in range(len(lines[i])):
                if lines[i][j] == '>':
                    fst_elem_x = j
                    fst_elem_y = (i - 1) // 2
                    snd_elem_x = j
                    snd_elem_y = (i + 1) // 2
                    constr_dict[fst_elem_y * 6 + fst_elem_x].append((mat_operator.MatSign.MORE, snd_elem_y * 6 + snd_elem_x))
                    constr_dict[snd_elem_y * 6 + snd_elem_x].append((mat_operator.MatSign.LESS, fst_elem_y * 6 + fst_elem_x))
                elif lines[i][j] == '<':
                    fst_elem_x = j
                    fst_elem_y = (i - 1) // 2
                    snd_elem_x = j
                    snd_elem_y = (i + 1) // 2
                    constr_dict[fst_elem_y * 6 + fst_elem_x].append((mat_operator.MatSign.LESS, snd_elem_y * 6 + snd_elem_x))
                    constr_dict[snd_elem_y * 6 + snd_elem_x].append((mat_operator.MatSign.MORE, fst_elem_y * 6 + fst_elem_x))

    #print(fut_matrix)
    #print(constr_dict)
    futoshiki_solver_bt(fut_matrix, (0,0), constr_dict)
    print(len(list_of_solutions_ft))

if __name__ == "__main__":
    #binary_8_8_bt()
    #get_next_elem_test()
    binary_6_6_fc()
    #binary_8_8_fc()
    #binary_10_10_bt()
    #binary_10_10_fc()
    #futoshiki_4_4()
    #futoshiki_5_5()
    #futoshiki_6_6()
    

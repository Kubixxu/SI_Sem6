
from copy import deepcopy
from random import randint

list_of_solutions_bin = []

def binary_solver_bt(bin_matrix, elem_cords):
    if elem_cords[1] == bin_matrix.shape[1]:
        list_of_solutions_bin.append(deepcopy(bin_matrix))
    elif bin_matrix[elem_cords[0]][elem_cords[1]] != -1:
        if bin_matrix.shape[0] - 1 == elem_cords[0]:
            binary_solver_bt(bin_matrix, (0, elem_cords[1] + 1))
        else:
            binary_solver_bt(bin_matrix, (elem_cords[0] + 1, elem_cords[1]))
    
    else:
        
        for val in range(0,2):
            bin_matrix[elem_cords[0]][elem_cords[1]] = val
            if not check_if_constr_brk(bin_matrix, elem_cords):
                #print(bin_matrix)
                if bin_matrix.shape[0] - 1 == elem_cords[0]:
                    binary_solver_bt(bin_matrix, (0, elem_cords[1] + 1))
                else:
                    binary_solver_bt(bin_matrix, (elem_cords[0] + 1, elem_cords[1]))
        """
        list_of_vals = [0,1]
        while len(list_of_vals) != 0:
            val_idx = randint(0, len(list_of_vals) - 1)
            val = list_of_vals[val_idx]
            bin_matrix[elem_cords[0]][elem_cords[1]] = val
            if not check_if_constr_brk(bin_matrix, elem_cords):
                #print(bin_matrix)
                if bin_matrix.shape[0] - 1 == elem_cords[0]:
                    binary_solver_bt(bin_matrix, (0, elem_cords[1] + 1))
                else:
                    binary_solver_bt(bin_matrix, (elem_cords[0] + 1, elem_cords[1]))
            list_of_vals.pop(val_idx)
        """

        bin_matrix[elem_cords[0]][elem_cords[1]] = -1

def binary_solver_bt_ran_pick(bin_matrix, free_spaces):
    if len(free_spaces) == 0:
        list_of_solutions_bin.append(deepcopy(bin_matrix))   
    else:
        #print(free_spaces)
        elem_cords = deepcopy(free_spaces[randint(0, len(free_spaces) - 1)])
        free_spaces.remove(elem_cords)
        #print(free_spaces)
        """
        for val in range(0,2):
            bin_matrix[elem_cords[0]][elem_cords[1]] = val
            if not check_if_constr_brk(bin_matrix, elem_cords):
                #print(bin_matrix)
                binary_solver_bt_ran_pick(bin_matrix, free_spaces)
        """
        list_of_vals = [0,1]
        while len(list_of_vals) != 0:
            val_idx = randint(0, len(list_of_vals) - 1)
            val = list_of_vals[val_idx]
            bin_matrix[elem_cords[0]][elem_cords[1]] = val
            if not check_if_constr_brk(bin_matrix, elem_cords):
                #print(bin_matrix)
                binary_solver_bt_ran_pick(bin_matrix, free_spaces)
            list_of_vals.pop(val_idx)

        free_spaces.append(elem_cords)
        bin_matrix[elem_cords[0]][elem_cords[1]] = -1

def binary_solver_fc(bin_matrix, elem_cords, num_of_propag):
    if elem_cords is None:

        list_of_solutions_bin.append(deepcopy(bin_matrix))
    elif bin_matrix[elem_cords[0]][elem_cords[1]] != -1:
            binary_solver_fc(bin_matrix, get_next_elem(elem_cords, bin_matrix.shape), num_of_propag)
    else:
        """
        for val in range(0,2):
            bin_matrix[elem_cords[0]][elem_cords[1]] = val
            if check_if_constr_brk(bin_matrix, elem_cords):
                bin_matrix[elem_cords[0]][elem_cords[1]] = -1
                continue
            bin_matrix[elem_cords[0]][elem_cords[1]] = -1
            bin_matrix_virt = deepcopy(bin_matrix)
            bin_matrix_virt[elem_cords[0]][elem_cords[1]] = val
            if check_if_constr_brk(bin_matrix_virt, elem_cords):
                continue
            current_cords = get_next_elem(elem_cords, bin_matrix_virt.shape)
            value_rejected = False
            for i in range(num_of_propag):
                if current_cords is None:
                    break
                has_non_empty_dom = False
                for next_elem_val in range(0,2):
                    bin_matrix_virt[current_cords[0]][current_cords[1]] = next_elem_val
                    if not check_if_constr_brk(bin_matrix_virt, current_cords):
                        has_non_empty_dom = True
                        break
                bin_matrix_virt[current_cords[0]][current_cords[1]] = -1
                if not has_non_empty_dom:
                    value_rejected = True
                    break
                current_cords = get_next_elem(current_cords, bin_matrix_virt.shape)
            if not value_rejected:
                bin_matrix[elem_cords[0]][elem_cords[1]] = val
                binary_solver_fc(bin_matrix, get_next_elem(elem_cords, bin_matrix.shape), num_of_propag)
        """
        list_of_vals = [0,1]
        while len(list_of_vals) != 0:
            val_idx = randint(0, len(list_of_vals) - 1)
            val = list_of_vals[val_idx]
            list_of_vals.pop(val_idx)
            bin_matrix[elem_cords[0]][elem_cords[1]] = val
            if check_if_constr_brk(bin_matrix, elem_cords):
                bin_matrix[elem_cords[0]][elem_cords[1]] = -1
                continue
            bin_matrix[elem_cords[0]][elem_cords[1]] = -1
            bin_matrix_virt = deepcopy(bin_matrix)
            bin_matrix_virt[elem_cords[0]][elem_cords[1]] = val
            if check_if_constr_brk(bin_matrix_virt, elem_cords):
                continue
            current_cords = get_next_elem(elem_cords, bin_matrix_virt.shape)
            value_rejected = False
            for i in range(num_of_propag):
                if current_cords is None:
                    break
                has_non_empty_dom = False
                for next_elem_val in range(0,2):
                    bin_matrix_virt[current_cords[0]][current_cords[1]] = next_elem_val
                    if not check_if_constr_brk(bin_matrix_virt, current_cords):
                        has_non_empty_dom = True
                        break
                bin_matrix_virt[current_cords[0]][current_cords[1]] = -1
                if not has_non_empty_dom:
                    value_rejected = True
                    break
                current_cords = get_next_elem(current_cords, bin_matrix_virt.shape)
            if not value_rejected:
                bin_matrix[elem_cords[0]][elem_cords[1]] = val
                binary_solver_fc(bin_matrix, get_next_elem(elem_cords, bin_matrix.shape), num_of_propag)
            
        
        
        bin_matrix[elem_cords[0]][elem_cords[1]] = -1

def binary_solver_fc_ran_pick(bin_matrix, free_spaces, num_of_propag):
    if len(free_spaces) == 0:
        list_of_solutions_bin.append(deepcopy(bin_matrix))
    else:
        elem_cords = deepcopy(free_spaces[randint(0, len(free_spaces) - 1)])
        free_spaces.remove(elem_cords)
        """
        for val in range(0,2):
            bin_matrix[elem_cords[0]][elem_cords[1]] = val
            if check_if_constr_brk(bin_matrix, elem_cords):
                bin_matrix[elem_cords[0]][elem_cords[1]] = -1
                continue
            bin_matrix[elem_cords[0]][elem_cords[1]] = -1
            bin_matrix_virt = deepcopy(bin_matrix)
            bin_matrix_virt[elem_cords[0]][elem_cords[1]] = val
            if check_if_constr_brk(bin_matrix_virt, elem_cords):
                continue
            current_cords = get_next_elem(elem_cords, bin_matrix_virt.shape)
            value_rejected = False
            for i in range(num_of_propag):
                if current_cords is None:
                    break
                has_non_empty_dom = False
                for next_elem_val in range(0,2):
                    bin_matrix_virt[current_cords[0]][current_cords[1]] = next_elem_val
                    if not check_if_constr_brk(bin_matrix_virt, current_cords):
                        has_non_empty_dom = True
                        break
                bin_matrix_virt[current_cords[0]][current_cords[1]] = -1
                if not has_non_empty_dom:
                    value_rejected = True
                    break
                current_cords = get_next_elem(current_cords, bin_matrix_virt.shape)
            if not value_rejected:
                bin_matrix[elem_cords[0]][elem_cords[1]] = val
                binary_solver_fc_ran_pick(bin_matrix, free_spaces, num_of_propag)
                
        """
        list_of_vals = [0,1]
        while len(list_of_vals) != 0:
            val_idx = randint(0, len(list_of_vals) - 1)
            val = list_of_vals[val_idx]
            list_of_vals.pop(val_idx)
            bin_matrix[elem_cords[0]][elem_cords[1]] = val
            if check_if_constr_brk(bin_matrix, elem_cords):
                bin_matrix[elem_cords[0]][elem_cords[1]] = -1
                continue
            bin_matrix[elem_cords[0]][elem_cords[1]] = -1
            bin_matrix_virt = deepcopy(bin_matrix)
            bin_matrix_virt[elem_cords[0]][elem_cords[1]] = val
            if check_if_constr_brk(bin_matrix_virt, elem_cords):
                continue
            current_cords = get_next_elem(elem_cords, bin_matrix_virt.shape)
            value_rejected = False
            for i in range(num_of_propag):
                if current_cords is None:
                    break
                has_non_empty_dom = False
                for next_elem_val in range(0,2):
                    bin_matrix_virt[current_cords[0]][current_cords[1]] = next_elem_val
                    if not check_if_constr_brk(bin_matrix_virt, current_cords):
                        has_non_empty_dom = True
                        break
                bin_matrix_virt[current_cords[0]][current_cords[1]] = -1
                if not has_non_empty_dom:
                    value_rejected = True
                    break
                current_cords = get_next_elem(current_cords, bin_matrix_virt.shape)
            if not value_rejected:
                bin_matrix[elem_cords[0]][elem_cords[1]] = val
                binary_solver_fc_ran_pick(bin_matrix, free_spaces, num_of_propag)
        
        free_spaces.append(elem_cords)
        bin_matrix[elem_cords[0]][elem_cords[1]] = -1

def get_next_elem(cords, mtrx_shape):
    if mtrx_shape[0] - 1 == cords[0] and mtrx_shape[1] - 1 == cords[1]:
        return None
    elif mtrx_shape[0] - 1 == cords[0]:
        return (0, cords[1] + 1)
    else:
        return (cords[0] + 1, cords[1])

def check_if_constr_brk(bin_matrix, elem_cords):

    ones_num = 0
    zeros_num = 0
    for col_idx in range(0, bin_matrix.shape[1]):
        if bin_matrix[elem_cords[0]][col_idx] == 0:
            zeros_num += 1
        elif bin_matrix[elem_cords[0]][col_idx] == 1:
            ones_num += 1
    if ones_num > bin_matrix.shape[0] / 2 or zeros_num > bin_matrix.shape[0] / 2:
        return True

    ones_num = 0
    zeros_num = 0
    for row_idx in range(0, bin_matrix.shape[0]):
        if bin_matrix[row_idx][elem_cords[1]] == 0:
            zeros_num += 1
        elif bin_matrix[row_idx][elem_cords[1]] == 1:
            ones_num += 1
    if ones_num > bin_matrix.shape[0] / 2 or zeros_num > bin_matrix.shape[0] / 2:
        return True
    
    if elem_cords[0] > 1 and bin_matrix[elem_cords[0] - 1][elem_cords[1]] == bin_matrix[elem_cords[0] - 2][elem_cords[1]] and bin_matrix[elem_cords[0]][elem_cords[1]] == bin_matrix[elem_cords[0] - 1][elem_cords[1]]:
        return True
    elif elem_cords[0] > 0 and elem_cords[0] < bin_matrix.shape[1] - 1 and bin_matrix[elem_cords[0] - 1][elem_cords[1]] == bin_matrix[elem_cords[0]][elem_cords[1]] and bin_matrix[elem_cords[0]][elem_cords[1]] == bin_matrix[elem_cords[0] + 1][elem_cords[1]]:
        return True    
    elif elem_cords[0] < bin_matrix.shape[1] - 2 and bin_matrix[elem_cords[0] + 1][elem_cords[1]] == bin_matrix[elem_cords[0] + 2][elem_cords[1]] and bin_matrix[elem_cords[0]][elem_cords[1]] == bin_matrix[elem_cords[0] + 1][elem_cords[1]]:
        return True
    elif elem_cords[1] > 1 and bin_matrix[elem_cords[0]][elem_cords[1] - 1] == bin_matrix[elem_cords[0]][elem_cords[1] - 2] and bin_matrix[elem_cords[0]][elem_cords[1]] == bin_matrix[elem_cords[0]][elem_cords[1] - 1]:
        return True
    elif elem_cords[1] < bin_matrix.shape[1] - 2 and bin_matrix[elem_cords[0]][elem_cords[1] + 1] == bin_matrix[elem_cords[0]][elem_cords[1] + 2] and bin_matrix[elem_cords[0]][elem_cords[1]] == bin_matrix[elem_cords[0]][elem_cords[1] + 1]:
        return True
    elif elem_cords[1] < bin_matrix.shape[1] - 1 and elem_cords[1] > 0 and bin_matrix[elem_cords[0]][elem_cords[1] + 1] == bin_matrix[elem_cords[0]][elem_cords[1]] and bin_matrix[elem_cords[0]][elem_cords[1]] == bin_matrix[elem_cords[0]][elem_cords[1] - 1]:
        return True

    
    curr_column = bin_matrix[:,elem_cords[1]]
    skip_col_check = False
    for el_idx in range(0, len(curr_column)):
        if curr_column[el_idx] !=-1:
            skip_col_check = True
    if not skip_col_check:
        for col_idx in range(0, bin_matrix.shape[1]):
            col = bin_matrix[:,col_idx]
            dont_check = False
            if col_idx != elem_cords[1]:
                for el_idx in range(0, len(curr_column)):
                    if col[el_idx] != -1:
                        dont_check = True
                        break
            if not dont_check:
                comparison = curr_column == col
                equal_arrays = comparison.all()
                if equal_arrays:
                    return True
    
    curr_row = bin_matrix[elem_cords[0]]
    skip_row_check = False
    for el_idx in range(0, len(curr_row)):
        if curr_row[el_idx] != -1:
            skip_row_check = True
    if not skip_row_check:
        for row_idx in range(0, bin_matrix.shape[1]):
            row = bin_matrix[row_idx]
            dont_check = False
            if row_idx != elem_cords[0]:
                for el_idx in range(0, len(curr_row)):
                    if row[el_idx] != -1:
                        dont_check = True
                        break
            if not dont_check:
                comparison = curr_row == row
                equal_arrays = comparison.all()
                if equal_arrays:
                    return True
    
    return False


"""
def get_next_elem_test():
    print(get_next_elem((0,0), (6,6)))
    print(get_next_elem((5,0), (6,6)))
    print(get_next_elem((5,2), (6,6)))
    print(get_next_elem((2,5), (6,6)))
    print(get_next_elem((5,5), (6,6)))

"""




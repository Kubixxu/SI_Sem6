from binary import binary_solver_fc
from random import randint
import mat_operator
from copy import deepcopy
list_of_solutions_ft = []


def futoshiki_solver_bt(fut_matrix, elem_cords, constr_dict, counter):
    if elem_cords[0] == fut_matrix.shape[1]:
        list_of_solutions_ft.append(deepcopy(fut_matrix))
        
    elif fut_matrix[elem_cords[0]][elem_cords[1]] != -1:
        counter.increment_by_one()
        if fut_matrix.shape[0] - 1 == elem_cords[1]:
            futoshiki_solver_bt(fut_matrix, (elem_cords[0] + 1, 0), constr_dict, counter)
        else:
            futoshiki_solver_bt(fut_matrix, (elem_cords[0], elem_cords[1] + 1), constr_dict, counter)
    else:
        counter.increment_by_one()
        """
        for val in range(1,fut_matrix.shape[0] + 1):
            fut_matrix[elem_cords[0]][elem_cords[1]] = val
            if not check_if_constr_brk(fut_matrix, elem_cords, constr_dict):
                #print(fut_matrix)
                if fut_matrix.shape[0] - 1 == elem_cords[1]:
                    futoshiki_solver_bt(fut_matrix, (elem_cords[0] + 1, 0), constr_dict, counter)
                else:
                    futoshiki_solver_bt(fut_matrix, (elem_cords[0], elem_cords[1] + 1), constr_dict, counter)
        """
        list_of_vals = list(range(1, fut_matrix.shape[0] + 1))
        while len(list_of_vals) != 0:
            val_idx = randint(0, len(list_of_vals) - 1)
            val = list_of_vals[val_idx]
            fut_matrix[elem_cords[0]][elem_cords[1]] = val
            if not check_if_constr_brk(fut_matrix, elem_cords, constr_dict):
                #print(fut_matrix)
                if fut_matrix.shape[0] - 1 == elem_cords[1]:
                    futoshiki_solver_bt(fut_matrix, (elem_cords[0] + 1, 0), constr_dict, counter)
                else:
                    futoshiki_solver_bt(fut_matrix, (elem_cords[0], elem_cords[1] + 1), constr_dict, counter)
            list_of_vals.pop(val_idx)
        
        fut_matrix[elem_cords[0]][elem_cords[1]] = -1

def futoshiki_solver_bt_ran_pick(fut_matrix, free_spaces, constr_dict, counter):
    if len(free_spaces) == 0:
        list_of_solutions_ft.append(deepcopy(fut_matrix))
        
    else:
        counter.increment_by_one()
        elem_cords = deepcopy(free_spaces[randint(0, len(free_spaces) - 1)])
        free_spaces.remove(elem_cords)
        
        for val in range(1,fut_matrix.shape[0] + 1):
            fut_matrix[elem_cords[0]][elem_cords[1]] = val
            if not check_if_constr_brk(fut_matrix, elem_cords, constr_dict):
                futoshiki_solver_bt_ran_pick(fut_matrix, free_spaces, constr_dict, counter)
        """
        list_of_vals = list(range(1, fut_matrix.shape[0] + 1))
        while len(list_of_vals) != 0:
            val_idx = randint(0, len(list_of_vals) - 1)
            val = list_of_vals[val_idx]
            fut_matrix[elem_cords[0]][elem_cords[1]] = val
            if not check_if_constr_brk(fut_matrix, elem_cords, constr_dict):
                futoshiki_solver_bt_ran_pick(fut_matrix, free_spaces, constr_dict, counter)
            list_of_vals.pop(val_idx)
        """

        free_spaces.append(elem_cords)
        fut_matrix[elem_cords[0]][elem_cords[1]] = -1

def futoshiki_solver_fc(fut_matrix, elem_cords, constr_dict, num_of_propag, counter):
    if elem_cords is None:

        list_of_solutions_ft.append(deepcopy(fut_matrix))

    elif fut_matrix[elem_cords[0]][elem_cords[1]] != -1:
        counter.increment_by_one()
        futoshiki_solver_fc(fut_matrix, get_next_elem(elem_cords, fut_matrix.shape), constr_dict, num_of_propag, counter)
    else:
        counter.increment_by_one()
        
        for val in range(1,fut_matrix.shape[0] + 1):
            fut_matrix[elem_cords[0]][elem_cords[1]] = val
            if check_if_constr_brk(fut_matrix, elem_cords, constr_dict):
                fut_matrix[elem_cords[0]][elem_cords[1]] = -1
                continue
            fut_matrix[elem_cords[0]][elem_cords[1]] = -1
            fut_matrix_virt = deepcopy(fut_matrix)
            fut_matrix_virt[elem_cords[0]][elem_cords[1]] = val
            if check_if_constr_brk(fut_matrix_virt, elem_cords, constr_dict):
                continue
            current_cords = get_next_elem(elem_cords, fut_matrix_virt.shape)
            value_rejected = False
            for i in range(num_of_propag):
                if current_cords is None:
                    break
                has_non_empty_dom = False
                for next_elem_val in range(1, fut_matrix_virt.shape[0] + 1):
                    fut_matrix_virt[current_cords[0]][current_cords[1]] = next_elem_val
                    if not check_if_constr_brk(fut_matrix_virt, current_cords, constr_dict):
                        has_non_empty_dom = True
                        break
                fut_matrix_virt[current_cords[0]][current_cords[1]] = -1
                if not has_non_empty_dom:
                    value_rejected = True
                    break
                current_cords = get_next_elem(current_cords, fut_matrix_virt.shape)
            if not value_rejected:
                fut_matrix[elem_cords[0]][elem_cords[1]] = val
                futoshiki_solver_fc(fut_matrix, get_next_elem(elem_cords, fut_matrix.shape), constr_dict, num_of_propag, counter)
        """
        list_of_vals = list(range(1, fut_matrix.shape[0] + 1))
        while len(list_of_vals) != 0:
            val_idx = randint(0, len(list_of_vals) - 1)
            val = list_of_vals[val_idx]
            list_of_vals.pop(val_idx)
            fut_matrix[elem_cords[0]][elem_cords[1]] = val
            if check_if_constr_brk(fut_matrix, elem_cords, constr_dict):
                fut_matrix[elem_cords[0]][elem_cords[1]] = -1
                continue
            fut_matrix[elem_cords[0]][elem_cords[1]] = -1
            fut_matrix_virt = deepcopy(fut_matrix)
            fut_matrix_virt[elem_cords[0]][elem_cords[1]] = val
            if check_if_constr_brk(fut_matrix_virt, elem_cords, constr_dict):
                continue
            current_cords = get_next_elem(elem_cords, fut_matrix_virt.shape)
            value_rejected = False
            for i in range(num_of_propag):
                if current_cords is None:
                    break
                has_non_empty_dom = False
                for next_elem_val in range(1, fut_matrix_virt.shape[0] + 1):
                    fut_matrix_virt[current_cords[0]][current_cords[1]] = next_elem_val
                    if not check_if_constr_brk(fut_matrix_virt, current_cords, constr_dict):
                        has_non_empty_dom = True
                        break
                fut_matrix_virt[current_cords[0]][current_cords[1]] = -1
                if not has_non_empty_dom:
                    value_rejected = True
                    break
                current_cords = get_next_elem(current_cords, fut_matrix_virt.shape)
            if not value_rejected:
                fut_matrix[elem_cords[0]][elem_cords[1]] = val
                futoshiki_solver_fc(fut_matrix, get_next_elem(elem_cords, fut_matrix.shape), constr_dict, num_of_propag, counter)
        """
        fut_matrix[elem_cords[0]][elem_cords[1]] = -1

def futoshiki_solver_fc_ran_pick(fut_matrix, free_spaces, constr_dict, num_of_propag, counter):
    if len(free_spaces) == 0:
        list_of_solutions_ft.append(deepcopy(fut_matrix))

    else:
        counter.increment_by_one()
        elem_cords = deepcopy(free_spaces[randint(0, len(free_spaces) - 1)])
        free_spaces.remove(elem_cords)
        
        for val in range(1,fut_matrix.shape[0] + 1):
            fut_matrix[elem_cords[0]][elem_cords[1]] = val
            if check_if_constr_brk(fut_matrix, elem_cords, constr_dict):
                fut_matrix[elem_cords[0]][elem_cords[1]] = -1
                continue
            fut_matrix[elem_cords[0]][elem_cords[1]] = -1
            fut_matrix_virt = deepcopy(fut_matrix)
            fut_matrix_virt[elem_cords[0]][elem_cords[1]] = val
            if check_if_constr_brk(fut_matrix_virt, elem_cords, constr_dict):
                continue
            current_cords = get_next_elem(elem_cords, fut_matrix_virt.shape)
            value_rejected = False
            for i in range(num_of_propag):
                if current_cords is None:
                    break
                has_non_empty_dom = False
                for next_elem_val in range(1, fut_matrix_virt.shape[0] + 1):
                    fut_matrix_virt[current_cords[0]][current_cords[1]] = next_elem_val
                    if not check_if_constr_brk(fut_matrix_virt, current_cords, constr_dict):
                        has_non_empty_dom = True
                        break
                fut_matrix_virt[current_cords[0]][current_cords[1]] = -1
                if not has_non_empty_dom:
                    value_rejected = True
                    break
                current_cords = get_next_elem(current_cords, fut_matrix_virt.shape)
            if not value_rejected:
                fut_matrix[elem_cords[0]][elem_cords[1]] = val
                futoshiki_solver_fc_ran_pick(fut_matrix, free_spaces, constr_dict, num_of_propag, counter)
        """
        list_of_vals = list(range(1, fut_matrix.shape[0] + 1))
        while len(list_of_vals) != 0:
            val_idx = randint(0, len(list_of_vals) - 1)
            val = list_of_vals[val_idx]
            list_of_vals.pop(val_idx)
            fut_matrix[elem_cords[0]][elem_cords[1]] = val
            if check_if_constr_brk(fut_matrix, elem_cords, constr_dict):
                fut_matrix[elem_cords[0]][elem_cords[1]] = -1
                continue
            fut_matrix[elem_cords[0]][elem_cords[1]] = -1
            fut_matrix_virt = deepcopy(fut_matrix)
            fut_matrix_virt[elem_cords[0]][elem_cords[1]] = val
            if check_if_constr_brk(fut_matrix_virt, elem_cords, constr_dict):
                continue
            current_cords = get_next_elem(elem_cords, fut_matrix_virt.shape)
            value_rejected = False
            for i in range(num_of_propag):
                if current_cords is None:
                    break
                has_non_empty_dom = False
                for next_elem_val in range(1, fut_matrix_virt.shape[0] + 1):
                    fut_matrix_virt[current_cords[0]][current_cords[1]] = next_elem_val
                    if not check_if_constr_brk(fut_matrix_virt, current_cords, constr_dict):
                        has_non_empty_dom = True
                        break
                fut_matrix_virt[current_cords[0]][current_cords[1]] = -1
                if not has_non_empty_dom:
                    value_rejected = True
                    break
                current_cords = get_next_elem(current_cords, fut_matrix_virt.shape)
            if not value_rejected:
                fut_matrix[elem_cords[0]][elem_cords[1]] = val
                futoshiki_solver_fc(fut_matrix, free_spaces, constr_dict, num_of_propag, counter)
        """
        free_spaces.append(elem_cords)
        fut_matrix[elem_cords[0]][elem_cords[1]] = -1

def get_next_elem(cords, mtrx_shape):
    if mtrx_shape[0] - 1 == cords[1] and mtrx_shape[1] - 1 == cords[0]:
        return None
    elif mtrx_shape[0] - 1 == cords[1]:
        return (cords[0] + 1, 0)
    else:
        return (cords[0], cords[1] + 1)

def check_if_constr_brk(fut_matrix, elem_cords, constr_dict):
    last_val = fut_matrix[elem_cords[0]][elem_cords[1]]

    curr_row = fut_matrix[elem_cords[0]]
    for idx in range(0, len(curr_row)):
        if idx != elem_cords[1] and curr_row[idx] == last_val:
            return True
    
    curr_column = fut_matrix[:,elem_cords[1]]
    for idx in range(0, len(curr_column)):
        if idx != elem_cords[0] and curr_column[idx] == last_val:
            return True
    
    
    curr_elem_constr = constr_dict[elem_cords[0] * fut_matrix.shape[1] + elem_cords[1]]
    for constr in curr_elem_constr:
        #print(str(constr[1] % fut_matrix.shape[1]) + ' ' + str(constr[1] // fut_matrix.shape[1]))
        #print(constr[1])
        if fut_matrix[constr[1] // fut_matrix.shape[0]][constr[1] % fut_matrix.shape[0]] != -1:
            if fut_matrix[constr[1] // fut_matrix.shape[0]][constr[1] % fut_matrix.shape[0]] >= last_val and constr[0] == mat_operator.MatSign.MORE:
                return True
            elif fut_matrix[constr[1] // fut_matrix.shape[0]][constr[1] % fut_matrix.shape[0]] <= last_val and constr[0] == mat_operator.MatSign.LESS:
                return True
    return False


import mat_operator
from copy import deepcopy
list_of_solutions_ft = []

def futoshiki_solver_bt(fut_matrix, elem_cords, constr_dict):
    if elem_cords[0] == fut_matrix.shape[1]:
        list_of_solutions_ft.append(deepcopy(fut_matrix))
    elif fut_matrix[elem_cords[0]][elem_cords[1]] != -1:
        if fut_matrix.shape[0] - 1 == elem_cords[1]:
            futoshiki_solver_bt(fut_matrix, (elem_cords[0] + 1, 0), constr_dict)
        else:
            futoshiki_solver_bt(fut_matrix, (elem_cords[0], elem_cords[1] + 1), constr_dict)
    else:
        for val in range(1,fut_matrix.shape[0] + 1):
            fut_matrix[elem_cords[0]][elem_cords[1]] = val
            if not check_if_constr_brk(fut_matrix, elem_cords, constr_dict):
                #print(fut_matrix)
                if fut_matrix.shape[0] - 1 == elem_cords[1]:
                    futoshiki_solver_bt(fut_matrix, (elem_cords[0] + 1, 0), constr_dict)
                else:
                    futoshiki_solver_bt(fut_matrix, (elem_cords[0], elem_cords[1] + 1), constr_dict)
        fut_matrix[elem_cords[0]][elem_cords[1]] = -1

def futoshiki_solver_fc(fut_matrix, elem_cords, constr_dict, num_of_propag):
    if elem_cords[0] == fut_matrix.shape[1]:
        list_of_solutions_ft.append(deepcopy(fut_matrix))
    elif fut_matrix[elem_cords[0]][elem_cords[1]] != -1:
        futoshiki_solver_fc(fut_matrix, get_next_elem(elem_cords, fut_matrix.shape), constr_dict, num_of_propag)
    else:
        for val in range(1,fut_matrix.shape[0] + 1):
            fut_matrix[elem_cords[0]][elem_cords[1]] = val
            if not check_if_constr_brk(fut_matrix, elem_cords, constr_dict):
                #print(fut_matrix)
                if fut_matrix.shape[0] - 1 == elem_cords[1]:
                    futoshiki_solver_bt(fut_matrix, (elem_cords[0] + 1, 0), constr_dict)
                else:
                    futoshiki_solver_bt(fut_matrix, (elem_cords[0], elem_cords[1] + 1), constr_dict)
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


from copy import deepcopy
import numpy as np
import pandas
import json

def take_the_best_genre(ct_dict):
    curr_max = 0
    curr_best = ""
    for key in ct_dict.keys():
        if ct_dict[key] > curr_max:
            curr_best = key
            curr_max = ct_dict[key]
    ct_dict.pop(curr_best)
    return curr_best

def json_str_to_one_genre(x):
    y = json.loads(x.strip())
    curr_index = 8
    curr_genre = ""
    for value in y.values():
            if value in chosen_genres and chosen_genres.index(value) < curr_index:
                curr_genre = value
                curr_index = chosen_genres.index(value)
    if curr_index != 8:
        return curr_genre
    else:
        return None

def f1score_mathched_elems(nb_engine, chunks_x, chunks_y):
    avg_matched = 0
    avg_f1_score = 0.0
    for chunk_idx in range(len(chunks_x)):
        curr_val_chunk_x = chunks_x[chunk_idx]
        curr_val_chunk_y = chunks_y[chunk_idx]
        tmp_chunks_x = list(chunks_x)
        tmp_chunks_y = list(chunks_y)
        tmp_chunks_x.pop(chunk_idx)
        tmp_chunks_y.pop(chunk_idx)
        
        curr_train_chunk_x = np.concatenate(tmp_chunks_x)
        curr_train_chunk_y = np.concatenate(tmp_chunks_y)
        y_pred = nb_engine.fit(curr_train_chunk_x, curr_train_chunk_y).predict(curr_val_chunk_x)
        avg_matched += (curr_val_chunk_y == y_pred).sum()
        avg_f1_score += f1_score(curr_val_chunk_y, y_pred)

data_to_process = pandas.read_csv('../booksummaries.txt', '\t', index_col=False, header=None)

data_to_process.drop(data_to_process.columns[[0, 1, 2, 3, 4]], axis=1, inplace=True)
data_to_process.dropna(inplace=True)

count_dict = {}
for column in data_to_process[[5]]:
    columnSeriesObj = data_to_process[column]
    for item in columnSeriesObj.values:
        y = json.loads(item.strip())
        for value in y.values():
            if count_dict.get(value):
                count_dict[value] = count_dict[value] + 1
            else:
                count_dict[value] = 1

#print(count_dict.values())
chosen_genres = []
for i in range(7):
    chosen_genres.append(take_the_best_genre(count_dict))

data_to_process[5] = data_to_process[5].transform(json_str_to_one_genre)

data_to_process.dropna(inplace=True)

cleansed_data = data_to_process.to_numpy()
train_n_validate_ds = cleansed_data[0:int(len(cleansed_data) * 0.9)]
test_ds = cleansed_data[int(len(cleansed_data) * 0.9):]
train_n_validate_ds_x = train_n_validate_ds[:,1]
train_n_validate_ds_y = train_n_validate_ds[:,0]
test_ds_x = test_ds[:,1]
test_ds_y = test_ds[:,0]

train_n_validate_ds_x_chunks = np.array_split(train_n_validate_ds_x, 10)
train_n_validate_ds_y_chunks = np.array_split(train_n_validate_ds_y, 10)


f1score_mathched_elems(None, train_n_validate_ds_x_chunks, train_n_validate_ds_y_chunks)
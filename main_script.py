from copy import deepcopy
from sklearn.metrics import f1_score, precision_score
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import GaussianNB, MultinomialNB, BernoulliNB, ComplementNB
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

def f1score_mathched_elems(nb_engine, vectorizer, chunks_x, chunks_y):
    avg_prec = 0.0
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
        y_pred = nb_engine.fit(vectorizer.transform(curr_train_chunk_x).toarray(), curr_train_chunk_y).predict(vectorizer.transform(curr_val_chunk_x).toarray())
        #print(curr_train_chunk_y)
        avg_prec += precision_score(curr_val_chunk_y, y_pred, average='weighted')
        avg_f1_score += f1_score(curr_val_chunk_y, y_pred, average='weighted')

    return (avg_prec / len(chunks_x), avg_f1_score / len(chunks_x))

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
vectorizer = TfidfVectorizer(stop_words='english', max_features=3000, decode_error='ignore')
vectorizer.fit(train_n_validate_ds_x)
train_n_validate_ds_y = train_n_validate_ds[:,0]
test_ds_x = test_ds[:,1]
test_ds_y = test_ds[:,0]

train_n_validate_ds_x_chunks = np.array_split(train_n_validate_ds_x, 10)
train_n_validate_ds_y_chunks = np.array_split(train_n_validate_ds_y, 10)

nb_engine1 = GaussianNB()
print('Gausian Naive Bayes results: ')
nb_eng_1_res = f1score_mathched_elems(nb_engine1, vectorizer, train_n_validate_ds_x_chunks, train_n_validate_ds_y_chunks)
print(nb_eng_1_res)

nb_engine2 = MultinomialNB()
print('Multinomial Naive Bayes results: ')
nb_eng_2_res = f1score_mathched_elems(nb_engine2, vectorizer, train_n_validate_ds_x_chunks, train_n_validate_ds_y_chunks)
print(nb_eng_2_res)

nb_engine3 = BernoulliNB()
print('Bernoulli Naive Bayes results: ')
nb_eng_3_res = f1score_mathched_elems(nb_engine3, vectorizer, train_n_validate_ds_x_chunks, train_n_validate_ds_y_chunks)
print(nb_eng_3_res)

nb_engine4 = ComplementNB()
print('Complement Naive Bayes results: ')
nb_eng_4_res = f1score_mathched_elems(nb_engine4, vectorizer, train_n_validate_ds_x_chunks, train_n_validate_ds_y_chunks)
print(nb_eng_4_res)

best_nb_engine = None
if nb_eng_1_res[0] + nb_eng_1_res[1] * 1.25 > nb_eng_2_res[0] + nb_eng_2_res[1] * 1.25 and nb_eng_1_res[0] + nb_eng_1_res[1] * 1.25 > nb_eng_3_res[0] + nb_eng_3_res[1] * 1.25 and nb_eng_1_res[0] + nb_eng_1_res[1] * 1.25 > nb_eng_4_res[0] + nb_eng_4_res[1] * 1.25:
    best_nb_engine = nb_engine1
elif nb_eng_2_res[0] + nb_eng_2_res[1] * 1.25 > nb_eng_1_res[0] + nb_eng_1_res[1] * 1.25 and nb_eng_2_res[0] + nb_eng_2_res[1] * 1.25 > nb_eng_3_res[0] + nb_eng_3_res[1] * 1.25 and nb_eng_2_res[0] + nb_eng_2_res[1] * 1.25 > nb_eng_4_res[0] + nb_eng_4_res[1] * 1.25:
    best_nb_engine = nb_engine2
elif nb_eng_3_res[0] + nb_eng_3_res[1] * 1.25 > nb_eng_1_res[0] + nb_eng_1_res[1] * 1.25 and nb_eng_3_res[0] + nb_eng_3_res[1] * 1.25 > nb_eng_2_res[0] + nb_eng_2_res[1] * 1.25 and nb_eng_3_res[0] + nb_eng_3_res[1] * 1.25 > nb_eng_4_res[0] + nb_eng_4_res[1] * 1.25:
    best_nb_engine = nb_engine3
else:
    best_nb_engine = nb_engine4

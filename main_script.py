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
chosen_genres = set()
for i in range(7):
    chosen_genres.add(take_the_best_genre(count_dict))


print(chosen_genres)

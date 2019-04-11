import numpy
import datetime
import pandas as pd
from google.cloud import storage
from sklearn.externals import joblib
from sklearn import linear_model
from sklearn.metrics import mean_squared_error
from math import sqrt
import requests

#survey results are n = 20, each in {0,1,2,3,4}

BUCKET_NAME = 'cs506finalprojectbucket'

# [START download-data]
# Public bucket holding the census data
bucket = storage.Client().bucket('cs506finalprojectbucket')

# Path to the data inside the public bucket
blob = bucket.blob('updatedData.csv')
# Download the data
blob.download_to_filename('updatedData.csv')
# [END download-data]

reg = linear_model.Lasso()
reg_healthcare = linear_model.Lasso()
reg_environment = linear_model.Lasso()
reg_abortion = linear_model.Lasso()
reg_military = linear_model.Lasso()
reg_importance_first = linear_model.Lasso()
reg_importance_second = linear_model.Lasso()
reg_importance_third = linear_model.Lasso()
reg_importance_fourth = linear_model.Lasso()
reg_importance_fifth = linear_model.Lasso()
regRidge = linear_model.Ridge(alpha=0.5)
regLasso = linear_model.Lasso(alpha=0.1)

# Roman: this code takes the data from my sample CSV and prints
# the array we need for ML. Please update to this when you are ready!
df = pd.read_csv('updatedData.csv')
#TODO change this section to pull from Austin/Peyton's database to get info about a particular database
test_user_responses = 0
#print(df.values[:,11:])
print(df.columns)
train_data_df = df.iloc[:90,11:] #TODO change size of training/testing data sets
train_labels_df = df.iloc[:90,6:11]
train_labels_df_importance = df.iloc[:90,1:6] #importance labels
train_data = train_data_df.values.tolist()
train_labels_all = train_labels_df.values.tolist()
train_labels_importance = train_labels_df_importance.values.tolist()
train_labels_abortion = []
train_labels = []
train_labels_healthcare = []
train_labels_environment = []
train_labels_military = []
train_labels_importance_first = []
train_labels_importance_second = []
train_labels_importance_third = []
train_labels_importance_fourth = []
train_labels_importance_fifth = []
for person in train_labels_all:
    train_labels_abortion.append(person[0])
    train_labels.append(person[1])  #extract answers for immigration
    train_labels_healthcare.append(person[2]) #extract for healthcare
    train_labels_environment.append(person[3])
    train_labels_military.append(person[4])
for person in train_labels_importance:
    train_labels_importance_first.append(person[0])
    train_labels_importance_second.append(person[1])
    train_labels_importance_third.append(person[2])
    train_labels_importance_fourth.append(person[3])
    train_labels_importance_fifth.append(person[4])
test_data_df = df.iloc[90:,11:]
test_labels_df = df.iloc[90:,6:11]
test_labels_importance_df = df.iloc[90:,1:6]
test_data = test_data_df.values.tolist()
test_labels_all = test_labels_df.values.tolist()
test_labels_importance = test_labels_importance_df.values.tolist()
test_labels = []
test_labels_abortion = []
test_labels_healthcare = []
test_labels_environment = []
test_labels_military = []
test_labels_importance_first = []
test_labels_importance_second = []
test_labels_importance_third = []
test_labels_importance_fourth = []
test_labels_importance_fifth = []
for person in test_labels_all:
    test_labels_abortion.append(person[0])
    test_labels.append(person[1])  #extract answers for immigration
    test_labels_healthcare.append(person[2])
    test_labels_environment.append(person[3])
    test_labels_military.append(person[4])
for person in test_labels_importance:
    test_labels_importance_first.append(person[0])
    test_labels_importance_second.append(person[1])
    test_labels_importance_third.append(person[2])
    test_labels_importance_fourth.append(person[3])
    test_labels_importance_fifth.append(person[4])
print(train_data)
print(train_labels)

#mock survey data for 5 people
mock_survey_data = [[4,1,0,4,0,1,3,4,4,3,3,2,3,0,1,0,0,1,0,3], #"democrat"
                    [0,3,3,0,4,3,0,1,0,0,0,0,1,3,3,3,4,3,3,1], #"republican"
                    [1,3,3,1,4,2,0,1,0,1,1,0,1,4,2,3,4,3,2,1], #"republican"
                    [2,2,1,1,2,3,2,3,2,2,2,2,2,2,3,2,2,3,3,3], #"moderate republican"
                    [3,1,1,3,2,2,3,3,3,3,3,3,3,1,2,1,1,2,1,2]] #"moderate democrat"

mock_opinions_immigration = [2,7,7,5,3]

reg.fit(train_data, train_labels)
reg_abortion.fit(train_data, train_labels_abortion)
reg_healthcare.fit(train_data, train_labels_healthcare)
reg_environment.fit(train_data, train_labels_environment)
reg_importance_first.fit(train_data,train_labels_importance_first)
reg_importance_second.fit(train_data,train_labels_importance_second)
reg_importance_third.fit(train_data,train_labels_importance_third)
reg_importance_fourth.fit(train_data,train_labels_importance_fourth)
reg_importance_fifth.fit(train_data,train_labels_importance_fifth)
reg_military.fit(train_data,train_labels_military)
regRidge.fit(train_data, train_labels)
regLasso.fit(train_data, train_labels)

print("oLS coefficients: ")
print(reg.coef_)

i = 0
total_prediction_array = []
health_pred = []
env_pred = []
abortion_pred = []
first_importance_pred = []
second_importance_pred = []
third_importance_pred = []
fourth_importance_pred = []
fifth_importance_pred = []
military_pred = []
for person in test_data:
    prediction = reg.predict(numpy.reshape(person, (1,-1)))
    prediction_abortion = reg_abortion.predict(numpy.reshape(person, (1,-1)))
    prediction_healthcare = reg_healthcare.predict(numpy.reshape(person, (1,-1)))
    prediction_environment = reg_environment.predict(numpy.reshape(person, (1,-1)))
    prediction_military = reg_military.predict(numpy.reshape(person, (1,-1)))
    prediction_first_importance = reg_importance_first.predict(numpy.reshape(person, (1,-1)))
    prediction_second_importance = reg_importance_second.predict(numpy.reshape(person, (1,-1)))
    prediction_third_importance = reg_importance_third.predict(numpy.reshape(person, (1,-1)))
    prediction_fourth_importance = reg_importance_fourth.predict(numpy.reshape(person, (1,-1)))
    prediction_fifth_importance = reg_importance_fifth.predict(numpy.reshape(person, (1,-1)))
    print(person)
    total_prediction_array.append(prediction)
    health_pred.append(prediction_healthcare)
    env_pred.append(prediction_environment)
    first_importance_pred.append(prediction_first_importance)
    second_importance_pred.append(prediction_second_importance)
    third_importance_pred.append(prediction_third_importance)
    fourth_importance_pred.append(prediction_fourth_importance)
    fifth_importance_pred.append(prediction_fifth_importance)
    abortion_pred.append(prediction_abortion)
    military_pred.append(prediction_military)
    #print(test_labels[i])
    print("new prediction (immigration) oLS, row " + str(i) + " of test set: " + str(prediction))
    print("person's real answer (immigration): " + str(test_labels[i]))
    print("new prediction (healthcare) oLS, row " + str(i) + " of test set: " + str(prediction_healthcare))
    print("person's real answer (healthcare): " + str(test_labels_healthcare[i]))
    print("new prediction (environment) oLS, row " + str(i) + " of test set: " + str(prediction_environment))
    print("person's real answer (environment): " + str(test_labels_environment[i]))
    print("new prediction (abortion) oLS, row " + str(i) + " of test set: " + str(prediction_abortion))
    print("person's real answer (abortion): " + str(test_labels_abortion[i]))
    print("new prediction (military) oLS, row " + str(i) + " of test set: " + str(prediction_military))
    print("person's real answer (military): " + str(test_labels_military[i]))
    print("new prediction (first importance issue) oLS, row " + str(i) + " of test set: " + str(prediction_first_importance))
    print("person's real answer (first importance issue): " + str(test_labels_importance_first[i]))
    print("new prediction (second importance issue) oLS, row " + str(i) + " of test set: " + str(prediction_second_importance))
    print("person's real answer (second importance issue): " + str(test_labels_importance_second[i]))
    print("new prediction (third importance issue) oLS, row " + str(i) + " of test set: " + str(prediction_third_importance))
    print("person's real answer (third importance issue): " + str(test_labels_importance_third[i]))
    print("new prediction (fourth importance issue) oLS, row " + str(i) + " of test set: " + str(prediction_fourth_importance))
    print("person's real answer (fourth importance issue): " + str(test_labels_importance_fourth[i]))
    print("new prediction (fifth importance issue) oLS, row " + str(i) + " of test set: " + str(prediction_fifth_importance))
    print("person's real answer (fifth importance issue): " + str(test_labels_importance_fifth[i]))  
    i += 1
# Export the model to a file
model = 'model.joblib'
joblib.dump(reg_importance_fifth, model)

# Upload the model to GCS
bucket = storage.Client().bucket(BUCKET_NAME)
blob = bucket.blob('{}/{}'.format(
    datetime.datetime.now().strftime('census_%Y%m%d_%H%M%S'),
    model))
blob.upload_from_filename(model)
print("RMSE is "+str(sqrt(mean_squared_error(test_labels,total_prediction_array))))
print("healthcare RMSE is "+str(sqrt(mean_squared_error(test_labels_healthcare,health_pred))))
print("military RMSE is "+str(sqrt(mean_squared_error(test_labels_military,military_pred))))
print("first importance RMSE is "+str(sqrt(mean_squared_error(test_labels_importance_first,first_importance_pred))))
print("second importance RMSE is "+str(sqrt(mean_squared_error(test_labels_importance_second,second_importance_pred))))
print("third importance RMSE is "+str(sqrt(mean_squared_error(test_labels_importance_third,third_importance_pred))))
print("fourth importance RMSE is "+str(sqrt(mean_squared_error(test_labels_importance_fourth,fourth_importance_pred))))
print("fifth importance RMSE is "+str(sqrt(mean_squared_error(test_labels_importance_fifth,fifth_importance_pred))))
print("abortion RMSE is "+str(sqrt(mean_squared_error(test_labels_abortion,abortion_pred))))

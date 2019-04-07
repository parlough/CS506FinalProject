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
blob = bucket.blob('myCopy.csv')
# Download the data
blob.download_to_filename('myCopy.csv')
# [END download-data]

reg = linear_model.LinearRegression()
reg_healthcare = linear_model.LinearRegression()
regRidge = linear_model.Ridge(alpha=0.5)
regLasso = linear_model.Lasso(alpha=0.1)

# Roman: this code takes the data from my sample CSV and prints
# the array we need for ML. Please update to this when you are ready!
df = pd.read_csv('myCopy.csv')
#TODO change this section to pull from Austin/Peyton's database to get info about a particular database
test_user_responses = 0
#print(df.values[:,11:])
print(df.columns)
train_data_df = df.iloc[:30,11:]
train_labels_df = df.iloc[:30,6:11]
train_data = train_data_df.values.tolist()
train_labels_all = train_labels_df.values.tolist()
train_labels = []
train_labels_healthcare = []
for person in train_labels_all:
    train_labels.append(person[1])  #extract answers for immigration
    train_labels_healthcare.append(person[2])
test_data_df = df.iloc[30:,11:]
test_labels_df = df.iloc[30:,6:11]
test_data = test_data_df.values.tolist()
test_labels_all = test_labels_df.values.tolist()
test_labels = []
test_labels_healthcare = []
for person in test_labels_all:
    test_labels.append(person[1])  #extract answers for immigration
    test_labels_healthcare.append(person[2])
print(train_data)
print(train_labels)

#mock survey data for 5 people
mock_survey_data = [[4,1,0,4,0,1,3,4,4,3,3,2,3,0,1,0,0,1,0,3], #"democrat"
                    [0,3,3,0,4,3,0,1,0,0,0,0,1,3,3,3,4,3,3,1], #"republican"
                    [1,3,3,1,4,2,0,1,0,1,1,0,1,4,2,3,4,3,2,1], #"republican"
                    [2,2,1,1,2,3,2,3,2,2,2,2,2,2,3,2,2,3,3,3], #"moderate republican"
                    [3,1,1,3,2,2,3,3,3,3,3,3,3,1,2,1,1,2,1,2]] #"moderate democrat"

#mock opinions on immigration
mock_opinions_immigration = [2,7,7,5,3]
#mock_opinions_immigration = [7,2,2,4,8]

#reg.fit(mock_survey_data, mock_opinions_immigration)
reg.fit(train_data, train_labels)
reg_healthcare.fit(train_data, train_labels_healthcare)
#regRidge.fit(mock_survey_data, mock_opinions_immigration)
regRidge.fit(train_data, train_labels)
#regLasso.fit(mock_survey_data, mock_opinions_immigration)
regLasso.fit(train_data, train_labels)

print("oLS coefficients: ")
print(reg.coef_)
#print("LS+ridge coefficients: ")
#print(regRidge.coef_)
#print("LASSO coefficients: ")
#print(regLasso.coef_)

#mock new person "democrat"
#mock_new_data1 = [4,1,0,3,1,2,3,3,4,3,3,3,3,1,1,1,1,2,1,3]
#predict their opinion on immigration
#pred1 = reg.predict(numpy.reshape(mock_new_data1, (1,-1)))
#predRidge1 = regRidge.predict(numpy.reshape(mock_new_data1, (1,-1)))
#predLasso1 = regLasso.predict(numpy.reshape(mock_new_data1, (1,-1)))
#print("new prediction oLS, mock democrat: " + str(pred1))
#print("new prediction LS+ridge, mock democrat: " + str(predRidge1))
#print("new prediction LASSO, mock democrat: " + str(predLasso1))

#mock new person "republican"
#mock_new_data2 = [0,4,3,0,3,3,1,1,0,0,0,1,1,3,3,4,3,3,2,1]
#predict their opinion on immigration
#pred2 = reg.predict(numpy.reshape(mock_new_data2, (1,-1)))
#predRidge2 = regRidge.predict(numpy.reshape(mock_new_data2, (1,-1)))
#predLasso2 = regLasso.predict(numpy.reshape(mock_new_data2, (1,-1)))
#print("new prediction oLS, mock republican: " + str(pred2))
#print("new prediction LS+ridge, mock republican: " + str(predRidge2))
#print("new prediction LASSO, mock republican: " + str(predLasso2))


#test on real people's answers from test set
#test_row1 = test_data[0]
#pred_real1 = reg.predict(numpy.reshape(test_row1, (1,-1)))
#print(test_row1)
#print(test_labels[0])
#print("new prediction oLS, row 1 of test set: " + str(pred_real1))
#print("person's real answer's: " + str(test_labels[0]))

#test_row2 = test_data[1]
#pred_real2 = reg.predict(numpy.reshape(test_row2, (1,-1)))
#print(test_row2)
#print(test_labels[1])
#print("new prediction oLS, row 2 of test set: " + str(pred_real2))
#print("person's real answer's: " + str(test_labels[1]))

i = 0
total_prediction_array = []
health_pred = []
for person in test_data:
    prediction = reg.predict(numpy.reshape(person, (1,-1)))
    prediction_healthcare = reg_healthcare.predict(numpy.reshape(person, (1,-1)))
    print(person)
    total_prediction_array.append(prediction)
    health_pred.append(prediction_healthcare)
    #print(test_labels[i])
    print("new prediction (immigration) oLS, row " + str(i) + " of test set: " + str(prediction))
    print("person's real answer (immigration): " + str(test_labels[i]))
    print("new prediction (healthcare) oLS, row " + str(i) + " of test set: " + str(prediction_healthcare))
    print("person's real answer (healthcare): " + str(test_labels_healthcare[i]))
    i += 1
# Export the model to a file
model = 'model.joblib'
joblib.dump(reg, model)

# Upload the model to GCS
bucket = storage.Client().bucket(BUCKET_NAME)
blob = bucket.blob('{}/{}'.format(
    datetime.datetime.now().strftime('census_%Y%m%d_%H%M%S'),
    model))
blob.upload_from_filename(model)
print("RMSE is "+str(sqrt(mean_squared_error(test_labels,total_prediction_array))))
print("healthcare RMSE is "+str(sqrt(mean_squared_error(test_labels_healthcare,health_pred))))

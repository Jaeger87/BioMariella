Per eseguire uno script python in java utilizzare il comando: Process p = Runtime.getRuntime().exec("python yourapp.py");
 
FaceDetection.py si occupa di eseguire il face detection delle foto contenute all'interno di una cartella: sarà necessario passare
	come argomento il nome della cartella in questione. 
walkImage.py invece ha lo scopo di eseguire il training sulla galleria. 
Infine FaceRecognizer si occupa della identificazione durante il login da parte dell'utente: lo script prende come argomento il nome 
	completo del singolo frame in questione. 
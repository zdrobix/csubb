#include <mpi.h>
#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

void run_app() {
	// get the rank
	int my_rank;
	MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);

	// get total number
	int number_of_processes;
	MPI_Comm_size(MPI_COMM_WORLD, &number_of_processes);

	if (my_rank == 0) {
		ifstream fin("Q:/info/csubb/Semestru 5/PPD/test/test_mpi/test_mpi/cartiere.txt");
			
		if (!fin) {
			cout << "Eroare la deschiderea din fisier.\n";
			return;
		}

		// citeste din fisier
		vector<int> temperaturi;
		vector<int> temperaturi_pt_cartier;

		while (!fin.eof()) {
			int t_aux;
			int count = 0;
			while (fin >> t_aux) {
				temperaturi.push_back(t_aux);
				count++;
			}
			temperaturi_pt_cartier.push_back(count);
		}

		fin.close();

		// adica cate cartiere sunt
		int segment_size = temperaturi_pt_cartier.size() / number_of_processes; 
		int rest = temperaturi_pt_cartier.size() % number_of_processes;

		// trimite numarul de temperaturi
		// trimite temperaturile
		for (int i = 1; i < number_of_processes; i++) {
			int index = temperaturi_pt_cartier[i];
			MPI_Send(&index, sizeof(int), MPI_INT, i, 0, MPI_COMM_WORLD);
			if (i == 1) {
				MPI_Send(&segment_size, sizeof(int), MPI_INT, 1, 2, MPI_COMM_WORLD);
			}
			MPI_Send(&temperaturi[index * i], index, MPI_INT, i, 1, MPI_COMM_WORLD);
		}

		vector<int> medii;

		// recieve results from other processes
		for (int i = 1; i < number_of_processes; i++) {
			int index = i * segment_size;
			MPI_Recv(&medii[index], segment_size, MPI_INT, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		}

		ofstream fout("Q:/info/csubb/Semestru 5/PPD/test/test_mpi/test_mpi/medii.txt");
		for (int i = 0; i < medii.size(); i++) {
			fout << medii[i] << endl;
		}
		fout.close();

	}
	else if (my_rank == 1) {
		std::vector<int> temperaturi;
		int numar_temperaturi;
		int segment_size;

		MPI_Recv(&numar_temperaturi, sizeof(int), MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		MPI_Recv(&segment_size, sizeof(int), MPI_INT, 0, 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		MPI_Recv(temperaturi.data(), numar_temperaturi * sizeof(int), MPI_INT, 0, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

		// compute segment
		float media = 0;
		int min = 9999; int max = -9999;
		for (int i = 0; i < numar_temperaturi; i++) {
			media += temperaturi[i];
			if (temperaturi[i] > max) max = temperaturi[i];
			if (temperaturi[i] < min) min = temperaturi[i];
		}
		media /= temperaturi.size();

		// send result back to process 0
		MPI_Send(&media, sizeof(int), MPI_INT, 1, 0, MPI_COMM_WORLD);


		// recieve min and max from other processes 
		vector<int> valori_minime;
		vector<int> valori_maxime;
		for (int i = 1; i < number_of_processes; i++) {
			int index = i * segment_size; 
			MPI_Recv(&valori_minime[index], segment_size, MPI_INT, i, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			MPI_Recv(&valori_maxime[index], segment_size, MPI_INT, i, 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		}

		int min_global = min; int max_global = max;
		for (int i = 0; i < valori_minime.size(); i++) {
			if (min_global > valori_minime[i]) min_global = valori_minime[i];
			if (max_global < valori_maxime[i]) max_global = valori_maxime[i];
		}

		ofstream fout("Q:/info/csubb/Semestru 5/PPD/test/test_mpi/test_mpi/minmax.txt");
		fout << min_global << " " << max_global;
		fout.close();
	}
	else if (my_rank > 1) {
		std::vector<int> temperaturi;
		int numar_temperaturi;

		MPI_Recv(&numar_temperaturi, sizeof(int), MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		MPI_Recv(temperaturi.data(), numar_temperaturi * sizeof(int), MPI_INT, 0, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

		// calculeaza min si max pentru fiecare
		float media = 0;
		int min = 9999; int max = -9999;
		for (int i = 0; i < numar_temperaturi; i++) {
			media += temperaturi[i];
			if (temperaturi[i] > max) max = temperaturi[i];
			if (temperaturi[i] < min) min = temperaturi[i];
		}
		media /= temperaturi.size();

		// send result back to process 0
		MPI_Send(&media, sizeof(int), MPI_INT, 1, 0, MPI_COMM_WORLD);

		// send result back to process 1
		MPI_Send(&min, sizeof(int), MPI_INT, 1, 1, MPI_COMM_WORLD);
		MPI_Send(&max, sizeof(int), MPI_INT, 1, 2, MPI_COMM_WORLD);
	}
}

int main() {
	MPI_Init(NULL, NULL);
	run_app();
	MPI_Finalize();
}
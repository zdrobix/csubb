#include <stdio.h>
#include "C:/Program Files (x86)/Microsoft SDKs/MPI/Include/mpi.h"
#include <string>
#include <iostream>
#include <vector>

void run_mpi_hello_world() {
	// get the rank of the process
	int my_rank;
	MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);

	// get total number 
	int number_of_processes;
	MPI_Comm_size(MPI_COMM_WORLD, &number_of_processes);

	if (my_rank == 0) {
		// process 0 recieves the messages from all other processes
		std::string result = "";
		char buffer[256];

		for (int i = 1; i < number_of_processes; i++) {
			MPI_Recv(buffer, 256, MPI_CHAR, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			result += buffer;
			if (i < number_of_processes - 1) {
				result += "\n";
			}
		}

		// display the final concatenated string
		std::cout << "Process 0 received messages:\n" << result << std::endl;
	}
	else {
		// other processes send their messages to process 0
		char message[256];
		sprintf_s(message, "Hello from process %d", my_rank);
		MPI_Send(message, strlen(message) + 1, MPI_CHAR, 0, 0, MPI_COMM_WORLD);
	}
}

void run_mpi_hello_world_async() {
	// get the rank
	int my_rank;
	MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);

	// get total number
	int number_of_processes;
	MPI_Comm_size(MPI_COMM_WORLD, &number_of_processes);

	if (my_rank == 0) {
		// Process 0: recieves messages asynchronously
		int num_other_processes = number_of_processes - 1;

		std::vector<char[256]> buffers(num_other_processes);
		std::vector<MPI_Request> requests(num_other_processes);
		std::vector<int> flags(num_other_processes, 0);

		for (int i = 1; i < num_other_processes + 1; i++) {
			MPI_Irecv(buffers[i - 1], 256, MPI_CHAR, i, 0, MPI_COMM_WORLD, &requests[i - 1]);
		}


		int recieved_count = 0;
		while (recieved_count < num_other_processes) {
			for (int i = 0; i < num_other_processes; i++) {
				if (flags[i] == 0) {
					// not recieved yet
					MPI_Test(&requests[i], &flags[i], MPI_STATUS_IGNORE);
					if (flags[i]) {
						// message recieved
						recieved_count++;
					}
				}
			}
		}
		// display all recieved messages
		std::string result = "";
		for (int i = 0; i < num_other_processes; i++) {
			result += buffers[i];
			if (i < num_other_processes - 1) {
				result += "\n";
			}
		}

		std::cout << "Process 0 recieved all messages asynchronously: " << result << std::endl;
	}
	else {
		char message[256];
		sprintf_s(message, "Hello from process %d", my_rank);

		MPI_Request request;
		MPI_Isend(message, strlen(message) + 1, MPI_CHAR, 0, 0, MPI_COMM_WORLD, &request);
	}


}

void run_mpi_vector_addition() {
	// get the rank
	int my_rank;
	MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);

	// get total number
	int number_of_processes;
	MPI_Comm_size(MPI_COMM_WORLD, &number_of_processes);
		
	const int VECTOR_SIZE = 100;
	int segment_size = VECTOR_SIZE / number_of_processes;
	
	if (my_rank == 0) {
		std::vector<int> A(VECTOR_SIZE);
		std::vector<int> B(VECTOR_SIZE);
		std::vector<int> C(VECTOR_SIZE);

		for (int i = 0; i < VECTOR_SIZE; i++) {
			A[i] = i;
			B[i] = i * 2;
		}

		// send segments to other processes
		for (int i = 1; i < number_of_processes; i++) {
			int index = i * segment_size;
			MPI_Send(&A[index], segment_size, MPI_INT, i, 0, MPI_COMM_WORLD);
			MPI_Send(&B[index], segment_size, MPI_INT, i, 1, MPI_COMM_WORLD);
		}

		// process own segment
		for (int i = 0; i < segment_size; i++) {
			C[i] = A[i] + B[i];
		}

		// recieve results from other processes
		for (int i = 1; i < number_of_processes; i++) {
			int index = i * segment_size;
			MPI_Recv(&C[index], segment_size, MPI_INT, i, 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		}

		// Display the result: first 10 elements
		std::cout << "Result vector C (first 10 elements): ";
		for (int i = 0; i < 10; i++) {
			std::cout << C[i] << " ";
		}
		std::cout << std::endl;

		std::cout << "Last 10 elements: ";
		for (int i = VECTOR_SIZE - 10; i < VECTOR_SIZE; i++) {
			std::cout << C[i] << " ";
		}
		std::cout << std::endl;
	}
	else {
		// other processes recieve segments
		std::vector<int> segment_A(segment_size);
		std::vector<int> segment_B(segment_size);
		std::vector<int> segment_C(segment_size);

		// recieves segments
		MPI_Recv(segment_A.data(), segment_size, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		MPI_Recv(segment_B.data(), segment_size, MPI_INT, 0, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

		// compute segment
		for (int i = 0; i < segment_size; i++) {
			segment_C[i] = segment_A[i] + segment_B[i];
		}

		// send result back to process 0
		MPI_Send(segment_C.data(), segment_size, MPI_INT, 0, 2, MPI_COMM_WORLD);
	}
}

int main(int argc, char** argv)
{
	MPI_Init(NULL, NULL);

	//run_mpi_hello_world();
	//run_mpi_hello_world_async();
	run_mpi_vector_addition();

	MPI_Finalize();
}

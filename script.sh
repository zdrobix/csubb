#!/bin/bash

# Specify the directory containing subdirectories
TARGET_DIR=$1

# Check if the target directory exists
if [ -d "$TARGET_DIR" ]; then
    # Iterate through each subdirectory
    for subdir in "$TARGET_DIR"/*; do
        # Check if it is a directory
        if [ -d "$subdir" ]; then
            # Create README.txt file in the subdirectory
	    rm "$subdir/README.txt"
            touch "$subdir/README.md"
            echo "README.txt created in $subdir"
        fi
    done
else
    echo "Target directory '$TARGET_DIR' does not exist."
fi

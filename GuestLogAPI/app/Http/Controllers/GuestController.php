<?php

namespace App\Http\Controllers;

use App\Models\Guest;
use Illuminate\Http\Request;

class GuestController extends Controller
{
    // 1. Retrieve all guests
    public function index()
    {
        return response()->json(Guest::all());
    }

    // 2. Add a new guest
    public function store(Request $request)
    {
        $validatedData = $request->validate([
            'name' => 'required|string|max:30',
            'email' => 'required|email|max:30',
            'time_in' => 'nullable|string',
            'date' => 'nullable|date',
            'phone_number' => 'nullable|string|max:20',
            'age' => 'nullable|integer',
        ]);

        $guest = Guest::create($validatedData);
        return response()->json($guest, 201); // 201 = Created
    }

    // 3. Retrieve a specific guest by ID
    public function show($id)
    {
        $guest = Guest::findOrFail($id);
        return response()->json($guest);
    }

    // 4. Update an existing guest
    public function update(Request $request, $id)
    {
        // Retrieve the guest to be updated
        $guest = Guest::findOrFail($id);

        // Validate incoming data (allow partial updates, so only the fields passed will be validated)
        $validatedData = $request->validate([
            'name' => 'nullable|string|max:30',
            'email' => 'nullable|email|max:30',
            'time_in' => 'nullable|string',
            'date' => 'nullable|date',
            'phone_number' => 'nullable|string|max:20',
            'age' => 'nullable|integer',
        ]);

        // Update the guest with the validated data
        $guest->update($validatedData);

        // Return the updated guest data as a JSON response
        return response()->json($guest);
    }

    // 5. Delete a guest
    public function destroy($id)
    {
        Guest::destroy($id);
        return response()->json(null, 204); // 204 = No Content
    }
}

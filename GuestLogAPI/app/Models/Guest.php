<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Concerns\HasUuids; // For UUID support 

class Guest extends Model
{
    use HasFactory, HasUuids;

    protected $keyType = 'string'; // Set the key type to string
    public $incrementing = false; // Disable auto-incrementing
    protected $fillable = [
        'name',
        'email',
        'time_in',
        'date',
        'phone_number',
        'age',
    ];
    
    // Mutator for converting camelCase to snake_case
    public function setAttribute($key, $value)
    {
        $key = \Illuminate\Support\Str::snake($key);
        parent::setAttribute($key, $value);
    }
    
}

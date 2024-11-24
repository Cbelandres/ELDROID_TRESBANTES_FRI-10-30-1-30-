    <?php

    use Illuminate\Database\Migrations\Migration;
    use Illuminate\Database\Schema\Blueprint;
    use Illuminate\Support\Facades\Schema;

    class CreateGuestsTable extends Migration
    {
        /**
         * Run the migrations.
         */
        public function up(): void
        {
            Schema::create('guests', function (Blueprint $table) {
                $table->uuid('id')->primary(); // Use UUID as primary key
                $table->string('name');
                $table->string('email');
                $table->string('time_in')->nullable();
                $table->date('date')->nullable();
                $table->string('phone_number')->nullable();
                $table->integer('age')->nullable();
                $table->timestamps();
            });
        }

        /**
         * Reverse the migrations.
         */
        public function down(): void
        {
            Schema::dropIfExists('guests');
        }
    }

